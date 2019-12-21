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
package com.quotorcloud.quotor.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.entity.SysDept;
import com.quotorcloud.quotor.admin.service.SysDeptService;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.log.annotation.SysLog;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

	@Autowired
	private SysDeptService sysDeptService;

	@PostMapping
	public R saveDept(DeptDTO deptDTO){
		return R.ok(sysDeptService.saveDept(deptDTO));
	}

	@PutMapping
	public R updateDept(DeptDTO deptDTO){
		return R.ok(sysDeptService.updateDept(deptDTO));
	}

	@GetMapping("list")
	public R listDept(Page page, DeptDTO deptDTO){
		return R.ok(sysDeptService.listDept(page, deptDTO));
	}

	@GetMapping("list/{id}")
	public R listDeptById(@PathVariable Integer id){
		return R.ok(sysDeptService.listDeptById(id));
	}

	@GetMapping("list-dept")
	public R listDeptAll(){
		return R.ok(sysDeptService.list(new QueryWrapper<>()));
	}

}
