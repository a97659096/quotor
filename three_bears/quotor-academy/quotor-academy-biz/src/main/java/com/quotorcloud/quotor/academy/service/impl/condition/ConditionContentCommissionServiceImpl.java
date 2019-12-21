package com.quotorcloud.quotor.academy.service.impl.condition;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionContentCommission;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionContentCommissionMapper;
import com.quotorcloud.quotor.academy.service.condition.ConditionContentCommissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目产品卡片提成表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-10
 */
@Service
public class ConditionContentCommissionServiceImpl extends ServiceImpl<ConditionContentCommissionMapper, ConditionContentCommission> implements ConditionContentCommissionService {

    @Autowired
    private ConditionContentCommissionMapper conditionContentCommissionMapper;

    /**
     * 设置内容提成
     * @param conditionContentCommission
     * @return
     */
    @Override
    public Boolean setContentCommission(ConditionContentCommission conditionContentCommission) {
        this.saveOrUpdate(conditionContentCommission);
        return Boolean.TRUE;
    }

    /**
     * 查询内容提成列表  contentType 1 项目， 2 产品 ，3 卡片
     * @param page
     * @param conditionContentCommission
     * @return
     */
    @Override
    public IPage<ConditionContentCommission> listContentCommission(Page page, ConditionContentCommission conditionContentCommission) {
        //当查询的是卡片的时候
        if(conditionContentCommission.getContentType().equals(3)){
            return conditionContentCommissionMapper.listCardCommissionPage(page, conditionContentCommission);
        //当查询为项目或者产品时
        }else {
            return conditionContentCommissionMapper.listProCommissionPage(page, conditionContentCommission);
        }
    }


}
