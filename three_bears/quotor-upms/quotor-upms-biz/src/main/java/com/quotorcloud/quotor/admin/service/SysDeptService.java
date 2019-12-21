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

package com.quotorcloud.quotor.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.dto.DeptTree;
import com.quotorcloud.quotor.admin.api.entity.SysDept;
import com.quotorcloud.quotor.admin.api.vo.DeptVO;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
public interface SysDeptService extends IService<SysDept> {

	/**
	 * 新增加盟商信息
	 * @param deptDTO
	 * @return
	 */
	Boolean saveDept(DeptDTO deptDTO);

	/**
	 * 修改加盟商信息
	 * @param deptDTO
	 * @return
	 */
	Boolean updateDept(DeptDTO deptDTO);

	/**
	 * 分页查询加盟商信息
	 * @param page
	 * @param deptDTO
	 * @return
	 */
	IPage<DeptVO> listDept(Page<SysDept> page, DeptDTO deptDTO);

	/**
	 * 根据id查询加盟商信息
	 * @param id
	 * @return
	 */
	DeptVO listDeptById(Integer id);


}
