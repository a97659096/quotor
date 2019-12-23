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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.quotorcloud.quotor.admin.api.dto.MenuTree;
import com.quotorcloud.quotor.admin.api.entity.SysMenu;
import com.quotorcloud.quotor.admin.api.vo.MenuVO;
import com.quotorcloud.quotor.admin.api.vo.TreeBuild;
import com.quotorcloud.quotor.common.core.util.TreeUtil;
import com.quotorcloud.quotor.admin.service.SysMenuService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.FileConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.FileUtil;
import com.quotorcloud.quotor.common.core.util.R;
import com.quotorcloud.quotor.common.log.annotation.SysLog;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {

	private final SysMenuService sysMenuService;

	private static final String ICON = "icon";

	/**
	 * 返回当前用户的树形菜单集合
	 *
	 * @return 当前用户的树形菜单
	 */
	@GetMapping
	public R getUserMenu() {
		// 获取符合条件的菜单
		Set<MenuVO> all = new HashSet<>();
		SecurityUtils.getRoles()
			.forEach(roleId -> all.addAll(sysMenuService.getMenuByRoleId(roleId)));
		List<MenuTree> menuTreeList = all.stream()
			.filter(menuVo -> CommonConstants.MENU.equals(menuVo.getType()))
			.map(MenuTree::new)
			.sorted(Comparator.comparingInt(MenuTree::getSort))
			.collect(Collectors.toList());
		return R.ok(TreeUtil.buildByLoop(menuTreeList, "-1"));
	}

	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public R getTree() {
		return R.ok(TreeBuild.buildTree(sysMenuService.list(Wrappers.emptyWrapper()), "-1"));
	}

	/**
	 * 返回角色的菜单集合
	 *
	 * @param roleId 角色ID
	 * @return 属性集合
	 */
	@GetMapping("/tree/{roleId}")
	public List getRoleTree(@PathVariable Integer roleId) {
		return sysMenuService.getMenuByRoleId(roleId)
			.stream()
			.map(MenuVO::getMenuId)
			.collect(Collectors.toList());
	}

	/**
	 * 通过ID查询菜单的详细信息
	 *
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable Integer id) {
		return R.ok(sysMenuService.getById(id));
	}

	/**
	 * 新增菜单
	 *
	 * @param sysMenu 菜单信息
	 * @return success/false
	 */
	@SysLog("新增菜单")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_add')")
	public R save(@Valid @RequestBody SysMenu sysMenu) {
		if(!ComUtil.isEmpty(sysMenu.getIconFile())){
			String icon = FileUtil.saveFile(sysMenu.getIconFile(), FileConstants.FileType.FILE_MENU_IMG_DIR, ICON);
			sysMenu.setIcon(icon);
		}
		return R.ok(sysMenuService.save(sysMenu));
	}

	/**
	 * 删除菜单
	 *
	 * @param id 菜单ID
	 * @return success/false
	 */
	@SysLog("删除菜单")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_menu_del')")
	public R removeById(@PathVariable Integer id) {
		return sysMenuService.removeMenuById(id);
	}

	/**
	 * 更新菜单
	 *
	 * @param sysMenu
	 * @return
	 */
	@SysLog("更新菜单")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_edit')")
	public R update(@Valid @RequestBody SysMenu sysMenu) {
		SysMenu serviceById = sysMenuService.getById(sysMenu.getMenuId());
		//处理图片删除
		if(!ComUtil.isEmpty(sysMenu.getIconFileString())){
			sysMenu.setIcon(FileUtil.removeFile(Lists.newArrayList(sysMenu.getIconFileString()),
					serviceById.getIcon(), FileConstants.FileType.FILE_MENU_IMG_DIR));
		}
		//处理图片新增
		if(!ComUtil.isEmpty(sysMenu.getIconFile())){
			String fileName = FileUtil.saveFile(sysMenu.getIconFile(),
					FileConstants.FileType.FILE_MENU_IMG_DIR, ICON);
			if(!ComUtil.isEmpty(fileName)){
				sysMenu.setIcon(fileName);
			}
		}
		return R.ok(sysMenuService.updateMenuById(sysMenu));
	}

}
