package com.quotorcloud.quotor.academy.service.impl.inventory;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionPro;
import com.quotorcloud.quotor.academy.api.entity.inventory.Inventory;
import com.quotorcloud.quotor.academy.mapper.inventory.InventoryMapper;
import com.quotorcloud.quotor.academy.service.condition.ConditionCategoryService;
import com.quotorcloud.quotor.academy.service.condition.ConditionProService;
import com.quotorcloud.quotor.academy.service.inventory.InventoryService;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.constant.enums.ExceptionEnum;
import com.quotorcloud.quotor.common.core.exception.MyException;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.DateTimeUtil;
import com.quotorcloud.quotor.common.core.util.PageUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 库存信息表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-10-31
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private ConditionProService conditionProService;

    @Autowired
    private ConditionCategoryService conditionCategoryService;

    /**
     * 库存统计查询
     * @param inventory
     * @return
     */
    @Override
    public JSONObject listSumInventory(Inventory inventory) {
        //设置店铺信息
        setShopInfo(inventory);
        Page<Inventory> page = PageUtil.getPage(inventory.getPageNo(),
                inventory.getPageSize());
        IPage<Inventory> inventories = inventoryMapper.selectSumInventoryPage(page,inventory);
        Integer totalAmount = inventoryMapper.selectSumInventory(inventory);
        return getJsonObject(inventories, totalAmount);
    }

    private void setShopInfo(Inventory inventory) {
        //设置店铺信息
        if(ComUtil.isEmpty(inventory.getShopId())){
            QuotorUser user = SecurityUtils.getUser();
            inventory.setShopId(String.valueOf(user.getDeptId()));
        }
    }

    /**
     * 删除信息
     * @param id
     * @return
     */
    @Override
    public Boolean removeInventory(String id) {
        Inventory inventory = inventoryMapper.selectById(id);
        //删除只需判断入库的值即可
        if(inventory.getType().equals(CommonConstants.IN_INVENTORY)) {
            List<Inventory> inventoryList = inventoryMapper.selectByProductId(inventory.getProductId());
            if (!ComUtil.isEmpty(inventoryList)) {
                //入库和
                int inSum = inventoryList.stream().filter(inve -> inve.getType().equals(CommonConstants.IN_INVENTORY))
                        .mapToInt(Inventory::getProductAmount).sum();
                //出库和
                int outSum = inventoryList.stream().filter(inve -> inve.getType().equals(CommonConstants.OUT_INVENTORY))
                        .mapToInt(Inventory::getProductAmount).sum();
                int sum = inSum - inventory.getProductAmount() - outSum;
                if (sum < 0) {
                    throw new MyException(117, "删除该条记录后该商品总库存余量将为负数，不允许删除");
                }
            }
        }
        inventory.setState(CommonConstants.STATUS_DEL);
        inventoryMapper.updateById(inventory);
        return Boolean.TRUE;
    }

    /**
     * 按id查询库存信息
     * @param id
     * @return
     */
    @Override
    public Inventory selectInventoryById(String id) {
        return inventoryMapper.selectById(id);
    }

    /**
     * 查询记录
     * @param inventory
     * @return
     */
    @Override
    public JSONObject listInventory(Inventory inventory) {
        //设置店铺信息
        setShopInfo(inventory);

        if(!ComUtil.isEmpty(inventory.getDateRange())){
            List<String> stringDate = DateTimeUtil.getStringDate(inventory.getDateRange());
            assert stringDate != null;
            inventory.setStart(stringDate.get(0));
            inventory.setEnd(stringDate.get(1));
        }
        //类别查询，查出当前类别和子类别所有id集合
        if(!ComUtil.isEmpty(inventory.getCategoryId())){
            List<String> categoryIds = conditionCategoryService.findCategoryIds(inventory.getCategoryId());
            inventory.setCategoryIds(categoryIds);
        }
        Page<Inventory> page = PageUtil.getPage(inventory.getPageNo(), inventory.getPageSize());
        IPage<Inventory> iPage = inventoryMapper.selectInventoryPage(page, inventory);
        Integer sum = inventoryMapper.selectSumInventoryGroupByType(inventory);
        return getJsonObject(iPage, sum);
    }

    //封装JSONOBj
    private JSONObject getJsonObject(IPage<Inventory> iPage, Integer sum) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("inventory", iPage.getRecords());
        jsonObject.put("total", iPage.getTotal());
        jsonObject.put("pageSize", iPage.getSize());
        jsonObject.put("currentPage", iPage.getCurrent());
        jsonObject.put("sum", sum);
        return jsonObject;
    }

    /**
     * 修改库存
     * @param inventory
     * @return
     */
    @Override
    public Boolean updateInventory(Inventory inventory) {
        Inventory byId = inventoryMapper.selectById(inventory.getId());
        List<Inventory> inventoryList = inventoryMapper.selectByProductId(inventory.getProductId());
        if(!ComUtil.isEmpty(inventoryList)) {
            //入库和
            int inSum = inventoryList.stream().filter(inve -> inve.getType().equals(CommonConstants.IN_INVENTORY))
                    .mapToInt(Inventory::getProductAmount).sum();
            //出库和
            int outSum = inventoryList.stream().filter(inve -> inve.getType().equals(CommonConstants.OUT_INVENTORY))
                    .mapToInt(Inventory::getProductAmount).sum();
            //如果是修改出库，检查库存是否足够
            if (inventory.getType().equals(CommonConstants.OUT_INVENTORY)) {
                int sum = inSum - (outSum - byId.getProductAmount() + inventory.getProductAmount());
                //如果库存量小于出库的数量则抛出异常
                if (sum < 0) {
                    throw new MyException(ExceptionEnum.PRODUCT_INVENTORY_NO_HAVE);
                }
            } else {
                //如果是入库，检查修改后的值是否会导致总库存为负数
                int sum = inSum - byId.getProductAmount() + inventory.getProductAmount() - outSum;
                if(sum < 0){
                    throw new MyException(118, "修改失败，修改后库存总和将为负数！");
                }
            }
        }
        setProductInfo(inventory);
        inventoryMapper.updateById(inventory);
        return Boolean.TRUE;
    }

    /**
     * TODO 店铺标识
     * 库存插入
     * @param inventory
     * @return
     */
    @Override
    public Boolean saveInventory(Inventory inventory) {
        //如果是出库则校验数量是否足够
        if(inventory.getType().equals(CommonConstants.OUT_INVENTORY)){
            //校验库存量是否足够
            inspectInventory(inventory.getProductAmount(), inventory.getProductId());
        }
        setProductInfo(inventory);
        inventoryMapper.insert(inventory);
        return Boolean.TRUE;
    }

    //查询出产品信息并赋值进inventory
    private void setProductInfo(Inventory inventory) {
        if(ComUtil.isEmpty(inventory.getProductId())){
           throw new MyException(118, "请选择商品信息");
        }
        String productId = inventory.getProductId();
        //根据逗号拆分字符串
        List<String> productList = Splitter.on(CommonConstants.SEPARATOR).splitToList(productId);
        if(!ComUtil.isEmpty(productList)){
            //把最后一级的id取出来set进去
            inventory.setProductId(productList.get(productList.size()-1));
        }
        //查询产品信息并赋值
        ConditionPro conditionPro = conditionProService.getOne(new QueryWrapper<ConditionPro>()
                .eq("p_id", inventory.getProductId()));
//        JSONObject jsonObject = testDataService.selectProductById(inventory.getProductId());
        if(ComUtil.isEmpty(conditionPro)){
            throw new MyException(ExceptionEnum.NOT_FIND_PRODUCT);
        }

        //设置店铺信息
        QuotorUser user = SecurityUtils.getUser();
        if(ComUtil.isEmpty(user)){
            return;
        }
        inventory.setShopId(String.valueOf(user.getDeptId()));
        inventory.setShopName(String.valueOf(user.getDeptName()));
    }

    private void inspectInventory(Integer outAmount, String productId) {
        List<Inventory> inventoryList = inventoryMapper.selectByProductId(productId);
        if(!ComUtil.isEmpty(inventoryList)) {
            //入库和
            int inSum = inventoryList.stream().filter(inve -> inve.getType().equals(CommonConstants.IN_INVENTORY))
                    .mapToInt(Inventory::getProductAmount).sum();
            //出库和
            int outSum = inventoryList.stream().filter(inve -> inve.getType().equals(CommonConstants.OUT_INVENTORY))
                    .mapToInt(Inventory::getProductAmount).sum();
            int sum = inSum - outSum;
            //如果库存量小于出库的数量则抛出异常
            if(sum < outAmount){
                throw new MyException(ExceptionEnum.PRODUCT_INVENTORY_NO_HAVE);
            }
        }else {
            throw new MyException(ExceptionEnum.PRODUCT_INVENTORY_NO_HAVE);
        }
    }
}
