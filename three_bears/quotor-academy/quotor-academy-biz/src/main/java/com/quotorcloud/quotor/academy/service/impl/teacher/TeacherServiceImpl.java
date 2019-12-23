package com.quotorcloud.quotor.academy.service.impl.teacher;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.quotorcloud.quotor.academy.api.dto.course.TeacherDTO;
import com.quotorcloud.quotor.academy.api.entity.teacher.Teacher;
import com.quotorcloud.quotor.academy.api.vo.teacher.TeacherVO;
import com.quotorcloud.quotor.academy.log.annotation.OperationLog;
import com.quotorcloud.quotor.academy.log.enums.OperationType;
import com.quotorcloud.quotor.academy.mapper.course.TeacherMapper;
import com.quotorcloud.quotor.academy.service.ListBoxService;
import com.quotorcloud.quotor.academy.service.course.TeacherService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.constant.ListBoxConstants;
import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import com.quotorcloud.quotor.common.core.util.PageUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 讲师信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-24
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ListBoxService listBoxService;

    //授课环境
    private static String ENVIRONMENTS = "environment/";
    //头像
    private static String HEAD_IMG = "headImg/";
    //护照
    private static String PASSPORT = "passport/";

    /**
     * 查询讲师国籍列表，把数据去重返回
     * @return
     */
    @Override
    public Set<String> selectTeacherNation() {
        List<Teacher> teacherNation = teacherMapper.selectTeacherNation();
        return teacherNation.stream().map(Teacher::getNationality).collect(Collectors.toSet());
    }

    /**
     * 按id查询实体
     * @param id id
     * @return
     */
    @Override
    public TeacherVO selectTeacherById(String id) {
        Teacher teacher = teacherMapper.selectById(id);
        return getTeacherVO(teacher);
    }

    /**
     * 下拉框，全部讲师信息
     * @return
     */
    @Override
    public List<JSONObject> listBoxTeacher() {
        List<Teacher> teachers = teacherMapper.selectList(new QueryWrapper<>());
        List<JSONObject> jsonObjects = Lists.newLinkedList();
        for (Teacher teacher : teachers){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", teacher.getId());
            jsonObject.put("phone", teacher.getPhone());
            jsonObject.put("name", teacher.getName());
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    /**
     * 删除讲师信息（逻辑删除）
     * @param id
     * @return
     */
    @Override
    @OperationLog(name = "删除讲师", contentType = 4, operatorRef = 0, idRef = 1, table = "bear_teacher",
            type = OperationType.DELETE, cloum = "t_name", idField = "t_id")
    public Boolean deleteTeacher(QuotorUser user, String id) {
        if(ComUtil.isEmpty(id)){
            throw new MyException(ExceptionEnum.NOT_FIND_ID);
        }
        try{
            Teacher teacher = new Teacher();
            teacher.setId(id);
            teacher.setDelState(CommonConstants.STATUS_DEL);
            teacherMapper.updateById(teacher);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ExceptionEnum.DELETE_TEACHER_FAILED);
        }
        return Boolean.TRUE;
    }

    /**
     * 修改信息
     * @param teacherDTO
     * @return
     */
    @Override
    @OperationLog(name = "修改讲师", contentType = 4, operatorRef = 0, operatorObj = 1, idRef = 2,table = "bear_teacher",
            type = OperationType.UPDATE, idField = "t_id")
    public Boolean updateTeacher(QuotorUser user, TeacherDTO teacherDTO, String id) {
        if(ComUtil.isEmpty(teacherDTO.getId())){
            throw new MyException(ExceptionEnum.NOT_FIND_ID);
        }
        try {
            Teacher teacher = teacherMapper.selectById(teacherDTO.getId());
            List<String> fileds = Lists.newArrayList("environment","passport","headImg");
            //对集合中字段进行删除文件操作
            FileUtil.removeFileAndField(teacher, teacherDTO, fileds, FileConstants.FileType.FILE_TEACHER_IMG_DIR);

            teacherMapper.updateById(mapTeacherDTOToDO(teacherDTO, teacher));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionEnum.UPDATE_TEACHER_FAILED);
        }
        return Boolean.TRUE;
    }

    /**
     * 查询讲师信息，支持按姓名、联系电话、国籍、在职状态查询
     * @param teacherDTO
     * @return
     */
    @Override
    public JSONObject listTeacher(TeacherDTO teacherDTO) {
        Page<Teacher> page = PageUtil.getPage(teacherDTO.getPageNo(), teacherDTO.getPageSize());
        //查询
        IPage<Teacher> teacherList = teacherMapper.selectTeacherPage(page, teacherDTO);
        List<TeacherVO> teacherVOS = Lists.newArrayList();
        if(!ComUtil.isEmpty(teacherList.getRecords())){
            //对查询结果涉及图片的进行重新赋值
            teacherList.getRecords().forEach(teacher -> {
                TeacherVO teacherVO = getTeacherVO(teacher);
                teacherVOS.add(teacherVO);
            });
        }
        return PageUtil.getPagePackage("teachers", teacherVOS, page);
    }

    /**
     * DO与VO映射
     * @param teacher
     * @return
     */
    private TeacherVO getTeacherVO(Teacher teacher) {
        TeacherVO teacherVO = new TeacherVO();
        BeanUtils.copyProperties(teacher, teacherVO);
        //对授课环境、护照进行重新赋值
        FileUtil.addBatchPrefix(teacher, TeacherVO.class, teacherVO,
                Lists.newArrayList("environment", "passport"));
        return teacherVO;
    }

    /**
     * 新增讲师信息
     * @param teacherDTO 讲师VO
     * @return
     */
    @Override
    @OperationLog(name = "新增讲师", contentType = 4, operatorRef = 0, operatorObj = 1,
            table = "bear_teacher", type = OperationType.ADD, cloum = "t_name")
    public Boolean saveTeacher(QuotorUser user, TeacherDTO teacherDTO) {
        try {
            Teacher teacher = new Teacher();
            teacherMapper.insert(mapTeacherDTOToDO(teacherDTO, teacher));
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ExceptionEnum.SAVE_FILE_FAILED);
        }
        return Boolean.TRUE;
    }

    /**
     * 对TeacherDTO与DO进行映射
     * @param teacherDTO
     * @param teacher
     * @throws Exception
     */
    private Teacher mapTeacherDTOToDO(TeacherDTO teacherDTO, Teacher teacher) throws Exception {
        //复制属性，排除头像、授课环境、护照
        BeanUtils.copyProperties(teacherDTO, teacher,
                "headImg", "environment", "passport");

        //处理国籍
        listBoxService.checkListBox(teacher.getNationality(), ListBoxConstants.TEACHER_MODULE,
                ListBoxConstants.TEACHER_MODULE_NATION);
        //插入头像并返回头像地址
        FileUtil.saveFileAndField(teacher, teacherDTO,
                Lists.newArrayList("headImg", "environment","passport"),
                FileConstants.FileType.FILE_TEACHER_IMG_DIR, null);
        return teacher;
    }


}
