/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.quotorcloud.quotor.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.dto.DeptTree;
import com.quotorcloud.quotor.admin.api.entity.SysDept;
import com.quotorcloud.quotor.admin.api.entity.SysDeptRelation;
import com.quotorcloud.quotor.admin.api.vo.DeptVO;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import com.quotorcloud.quotor.common.core.util.TreeUtil;
import com.quotorcloud.quotor.admin.mapper.SysDeptMapper;
import com.quotorcloud.quotor.admin.service.SysDeptRelationService;
import com.quotorcloud.quotor.admin.service.SysDeptService;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	@Autowired
	private SysDeptMapper sysDeptMapper;

	/**
	 * 插入加盟商信息
	 * @param deptDTO
	 * @return
	 */
	@Override
	public Boolean saveDept(DeptDTO deptDTO) {
		SysDept sysDept = new SysDept();
		//属性拷贝
		BeanUtils.copyProperties(deptDTO, sysDept, "headImg", "environment");
		//存入图片
		FileUtil.saveFileAndField(sysDept, deptDTO, Lists.newArrayList("headImg", "environment"),
				FileConstants.FileType.FILE_DEPT_IMG_DIR, null);
		//插入操作
		sysDeptMapper.insert(sysDept);
		return Boolean.TRUE;
	}

	/**
	 * 修改加盟商信息
	 * @param deptDTO
	 * @return
	 */
	@Override
	public Boolean updateDept(DeptDTO deptDTO) {
		//查询部门信息
		SysDept sysDept = sysDeptMapper.selectById(deptDTO.getDeptId());
		//扣除短信条数
		if(!ComUtil.isEmpty(deptDTO.getMessageCount())){
			Integer surplus = sysDept.getMessageCount() - deptDTO.getMessageCount();
			sysDept.setMessageCount(surplus);
		}
		//属性拷贝
		BeanUtils.copyProperties(deptDTO, sysDept,"headImg", "environment");
		//删除需要删除的图片
		FileUtil.removeFileAndField(sysDept, deptDTO,
				Lists.newArrayList("headImg", "environment"), FileConstants.FileType.FILE_DEPT_IMG_DIR);
		//新增需要新增的图片
		FileUtil.saveFileAndField(sysDept, deptDTO, Lists.newArrayList("headImg", "environment"),
				FileConstants.FileType.FILE_DEPT_IMG_DIR, null);
		sysDeptMapper.updateById(sysDept);
		return Boolean.TRUE;
	}

	/**
	 * 分页查询加盟商信息
	 * @param page
	 * @param deptDTO
	 * @return
	 */
	@Override
	public IPage<DeptVO> listDept(Page<SysDept> page, DeptDTO deptDTO) {
		IPage<DeptVO> deptVOS = sysDeptMapper.listDeptPage(page, deptDTO);
		for (DeptVO deptVO : deptVOS.getRecords()){
			deptVO.setEnvironment(FileUtil.getJsonObjects(deptVO.getEnvironmentDatabase()));
		}
		return deptVOS;
	}

	/**
	 * 按id查找加盟商信息
	 * @param id
	 * @return
	 */
	@Override
	public DeptVO listDeptById(Integer id) {
		DeptVO deptVO = sysDeptMapper.selectDeptById(id);
		deptVO.setEnvironment(FileUtil.getJsonObjects(deptVO.getEnvironmentDatabase()));
		return deptVO;
	}
}
