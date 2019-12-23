package com.quotorcloud.quotor.admin.api.vo;

import com.quotorcloud.quotor.admin.api.dto.MenuTree;
import com.quotorcloud.quotor.admin.api.entity.SysMenu;
import com.quotorcloud.quotor.common.core.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeBuild {

    public static List<MenuTree> buildTree(List<SysMenu> menus, String root) {
		List<MenuTree> trees = new ArrayList<>();
		MenuTree node;
		for (SysMenu menu : menus) {
			node = new MenuTree();
			node.setId(String.valueOf(menu.getMenuId()));
			node.setParentId(String.valueOf(menu.getParentId()));
			node.setName(menu.getName());
			node.setPermission(menu.getPermission());
			node.setSort(menu.getSort());
			node.setPath(menu.getPath());
			node.setCode(menu.getPermission());
			node.setLabel(menu.getName());
			node.setComponent(menu.getComponent());
			node.setIcon(menu.getIcon());
			node.setType(menu.getType());
			node.setKeepAlive(menu.getKeepAlive());
			trees.add(node);
		}
		return TreeUtil.buildByLoop(trees, root);
	}

}
