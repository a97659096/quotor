package com.quotorcloud.quotor.academy.mapper.inventory;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.inventory.Inventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 库存信息表 Mapper 接口
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-31
 */
public interface InventoryMapper extends BaseMapper<Inventory> {

    List<Inventory> selectByProductId(@Param("productId") String productId);

    IPage<Inventory> selectSumInventoryPage(Page<Inventory> page, @Param("inven") Inventory inventory);

    IPage<Inventory> selectInventoryPage(Page<Inventory> page, @Param("inven") Inventory inventory);

    Integer selectSumInventoryGroupByType(@Param("inven")Inventory inventory);

    Integer selectSumInventory(@Param("inven")Inventory inventory);
}
