package com.quotorcloud.quotor.academy.service.impl.inn;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.dto.inn.InnTeacherDTO;
import com.quotorcloud.quotor.academy.api.entity.inn.InnTeacher;
import com.quotorcloud.quotor.academy.mapper.inn.InnTeacherMapper;
import com.quotorcloud.quotor.academy.service.inn.InnTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 下店老师信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-28
 */
@Service
public class InnTeacherServiceImpl extends ServiceImpl<InnTeacherMapper, InnTeacher> implements InnTeacherService {

    @Autowired
    private InnTeacherMapper innTeacherMapper;

    @Override
    public Boolean saveInnTeacher(InnTeacherDTO innTeacherDTO) {
        InnTeacher innTeacher = new InnTeacher();
        innTeacher.setDelState(CommonConstants.STATUS_NORMAL);
        BeanUtils.copyProperties(innTeacherDTO, innTeacher);
        FileUtil.saveFileAndField(innTeacher, innTeacherDTO, "headImg", FileConstants.FileType.FILE_INN_IMG_DIR, null);
        innTeacherMapper.insert(innTeacher);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateInnTeacher(InnTeacherDTO innTeacherDTO) {
        InnTeacher innTeacher = innTeacherMapper.selectById(innTeacherDTO.getId());
        BeanUtils.copyProperties(innTeacherDTO, innTeacher);
        //处理图片
        FileUtil.removeFileAndField(innTeacher, innTeacherDTO, "headImg", FileConstants.FileType.FILE_INN_IMG_DIR);
        FileUtil.saveFileAndField(innTeacher, innTeacherDTO, "headImg", FileConstants.FileType.FILE_INN_IMG_DIR, null);
        innTeacherMapper.updateById(innTeacher);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeInnTeacher(String id) {
        innTeacherMapper.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public IPage<InnTeacher> listInnTeacher(Page<InnTeacher> page, InnTeacherDTO innTeacherDTO) {
        return innTeacherMapper.selectPage(page, new QueryWrapper<>());
    }

    @Override
    public InnTeacher selectInnTeacherById(String id) {
        return innTeacherMapper.selectById(id);
    }
}
