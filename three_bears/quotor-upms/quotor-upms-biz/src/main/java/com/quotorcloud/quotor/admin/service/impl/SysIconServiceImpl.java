package com.quotorcloud.quotor.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.admin.api.dto.IconDTO;
import com.quotorcloud.quotor.admin.api.entity.SysIcon;
import com.quotorcloud.quotor.admin.mapper.SysIconMapper;
import com.quotorcloud.quotor.admin.service.SysIconService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 图标管理 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
@Service
public class SysIconServiceImpl extends ServiceImpl<SysIconMapper, SysIcon> implements SysIconService {

    @Autowired
    private SysIconMapper sysIconMapper;

    @Override
    public Boolean saveIcon(SysIcon sysIcon) {
        sysIcon.setDelState(CommonConstants.STATUS_NORMAL);
        sysIconMapper.insert(sysIcon);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateIcon(SysIcon sysIcon) {
        sysIconMapper.updateById(sysIcon);
        return Boolean.TRUE;
    }

    @Override
    public JSONObject selectIcon(IconDTO iconDTO) {
        Page<SysIcon> page = PageUtil.getPage(iconDTO.getPageNo(), iconDTO.getPageSize());
        IPage<SysIcon> sysIconIPage = sysIconMapper.selectPage(page, new QueryWrapper<SysIcon>()
                .eq("del_state", CommonConstants.STATUS_NORMAL)
                .like(!ComUtil.isEmpty(iconDTO.getDescription()),
                "description", iconDTO.getDescription())
                .eq(!ComUtil.isEmpty(iconDTO.getState()), "state", iconDTO.getState())
                .orderByDesc("gmt_create")
                .orderByAsc("state"));
        return PageUtil.getPagePackage("icons", sysIconIPage.getRecords(), page);
    }

    @Override
    public Boolean removeIcon(String id) {
        SysIcon sysIcon = new SysIcon();
        sysIcon.setId(id);
        sysIcon.setDelState(CommonConstants.STATUS_DEL);
        sysIconMapper.updateById(sysIcon);
        return Boolean.TRUE;
    }
}
