package com.quotorcloud.quotor.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.admin.api.dto.IconDTO;
import com.quotorcloud.quotor.admin.api.entity.SysIcon;

/**
 * <p>
 * 图标管理 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-11-11
 */
public interface SysIconService extends IService<SysIcon> {

    Boolean saveIcon(SysIcon sysIcon);

    Boolean updateIcon(SysIcon sysIcon);

    JSONObject selectIcon(IconDTO iconDTO);

    Boolean removeIcon(String id);

}
