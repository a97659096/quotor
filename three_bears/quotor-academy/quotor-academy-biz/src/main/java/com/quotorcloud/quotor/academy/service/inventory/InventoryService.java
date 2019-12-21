package com.quotorcloud.quotor.academy.service.inventory;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.entity.inventory.Inventory;

/**
 * <p>
 * 库存信息表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-31
 */
public interface InventoryService extends IService<Inventory> {

    Boolean saveInventory(Inventory inventory);

    Boolean updateInventory(Inventory inventory);

    JSONObject listInventory(Inventory inventory);

    JSONObject listSumInventory(Inventory inventory);

    Boolean removeInventory(String id);

    Inventory selectInventoryById(String id);
}
