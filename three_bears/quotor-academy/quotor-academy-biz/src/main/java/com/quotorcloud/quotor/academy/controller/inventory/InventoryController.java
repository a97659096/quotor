package com.quotorcloud.quotor.academy.controller.inventory;


import com.quotorcloud.quotor.academy.api.entity.inventory.Inventory;
import com.quotorcloud.quotor.academy.service.inventory.InventoryService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 库存信息表 前端控制器
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-31
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * 新增库存
     * @param inventory
     * @return
     */
    @PostMapping("save")
    public R saveInventory(@RequestBody Inventory inventory){
        return R.ok(inventoryService.saveInventory(inventory));
    }

    /**
     * 查询库存列表
     * @param inventory
     * @return
     */
    @GetMapping("list")
    public R listInventory(Inventory inventory){
        return R.ok(inventoryService.listInventory(inventory));
    }

    /**
     * 查询并求和
     * @param inventory
     * @return
     */
    @GetMapping("list/sum")
    public R listSumInventory(Inventory inventory){
        return R.ok(inventoryService.listSumInventory(inventory));
    }

    /**
     * 按id查询库存信息
     * @param id
     * @return
     */
    @GetMapping("list/{id}")
    public R selectInventoryById(@PathVariable String id){
        return R.ok(inventoryService.selectInventoryById(id));
    }

    /**
     * 修改库存信息
     * @param inventory
     * @return
     */
    @PutMapping("update")
    public R updateInventory(@RequestBody Inventory inventory){
        return R.ok(inventoryService.updateInventory(inventory));
    }

    /**
     * 删除库存信息
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public R deleteInventory(@PathVariable String id){
        return R.ok(inventoryService.removeInventory(id));
    }

}
