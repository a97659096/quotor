package com.quotorcloud.quotor.academy.service.impl.condition;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.quotorcloud.quotor.academy.api.entity.ListBox;
import com.quotorcloud.quotor.academy.api.entity.condition.ConditionLadderCommission;
import com.quotorcloud.quotor.academy.mapper.ListBoxMapper;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionLadderCommissionMapper;
import com.quotorcloud.quotor.academy.service.condition.ConditionLadderCommissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quotorcloud.quotor.academy.util.ScheduleUtils;
import com.quotorcloud.quotor.academy.util.ShopSetterUtil;
import com.quotorcloud.quotor.common.core.constant.CommonConstants;
import com.quotorcloud.quotor.common.core.exception.MyException;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 阶梯提成表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-16
 */
@Service
public class ConditionLadderCommissionServiceImpl extends ServiceImpl<ConditionLadderCommissionMapper, ConditionLadderCommission> implements ConditionLadderCommissionService {

    @Autowired
    private ConditionLadderCommissionMapper conditionLadderCommissionMapper;

    @Autowired
    private ShopSetterUtil shopSetterUtil;

    @Autowired
    private ListBoxMapper listBoxMapper;

    /** 调度工厂Bean */
    @Autowired
    private Scheduler scheduler;

    public void initScheduleJob() {
        List<ConditionLadderCommission> scheduleJobList = conditionLadderCommissionMapper
                .selectList(new QueryWrapper<ConditionLadderCommission>());
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ConditionLadderCommission scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils
                    .getCronTrigger(scheduler, scheduleJob.getName(), scheduleJob.getShopId());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }


    /**
     * 新增阶梯提成
     * @param conditionLadderCommission
     * @return
     */
    @Override
    public Boolean saveConditionLadderCommission(ConditionLadderCommission conditionLadderCommission) {

        //设置店铺信息
        shopSetterUtil.shopSetter(conditionLadderCommission, conditionLadderCommission.getShopId());

        //检查名称是否已经存在
        checkLadderIsExist(conditionLadderCommission);

        //设置职位名称
        setPositionNames(conditionLadderCommission);

        //保存操作
        this.save(conditionLadderCommission);

        //设置cron表达式
        setCron(conditionLadderCommission);
        //创建定时任务
        ScheduleUtils.createScheduleJob(scheduler, conditionLadderCommission);

        return Boolean.TRUE;
    }

    /**
     * 修改阶梯提成
     * @param conditionLadderCommission
     * @return
     */
    @Override
    public Boolean updateConditionLadderCommission(ConditionLadderCommission conditionLadderCommission) {
        //检查是否已经存在
        this.checkLadderIsExist(conditionLadderCommission);
        //设置职位名称
        this.setPositionNames(conditionLadderCommission);
        //设置cron表达式
        this.setCron(conditionLadderCommission);
        //修改定时任务
        ScheduleUtils.updateScheduleJob(scheduler, conditionLadderCommission);
        //修改操作
        this.updateById(conditionLadderCommission);
        return Boolean.TRUE;
    }

    /**
     * 删除阶梯提成
     * @param id
     * @return
     */
    @Override
    public Boolean removeConditionLadderCommission(String id) {
        //删除定时任务
        ConditionLadderCommission conditionLadderCommission = this.getById(id);
        ScheduleUtils.deleteScheduleJob(scheduler, conditionLadderCommission.getName(),
                conditionLadderCommission.getShopId());
        //删除数据
        this.removeById(id);
        return Boolean.TRUE;
    }

    /**
     * 分页查询
     * @param page
     * @param conditionLadderCommission
     * @return
     */
    @Override
    public IPage listPageConditionLadderCommission(Page page, ConditionLadderCommission conditionLadderCommission) {
        //设置店铺信息
        shopSetterUtil.shopSetter(conditionLadderCommission, conditionLadderCommission.getShopId());

        //分页查询
        return conditionLadderCommissionMapper.selectPage(page, Wrappers.<ConditionLadderCommission>query()
                .lambda().eq(ConditionLadderCommission::getShopId, conditionLadderCommission.getShopId()));
    }

    private void checkLadderIsExist(ConditionLadderCommission conditionLadderCommission) {
        //检查规则名称是否已经存在
        int count = this.count(Wrappers.<ConditionLadderCommission>query().lambda()
                .eq(ConditionLadderCommission::getName, conditionLadderCommission.getName())
                .eq(ConditionLadderCommission::getShopId, conditionLadderCommission.getShopId()));
        //如果大于0则说明已经存在，抛出异常
        if(count>0){
            throw new MyException("规则已经存在，请重新添加！");
        }
    }

    /**
     * 设置cron表达式
     * @param conditionLadderCommission
     */
    private void setCron(ConditionLadderCommission conditionLadderCommission) {
        Integer accountPeriod = conditionLadderCommission.getAccountPeriod();
        String cron;
        switch (accountPeriod){
            //每周周一 1:00:00 执行一次
            case 1:
                cron = "0 0 1 ? * MON";
                break;
            //每月一号 1:00:00 执行一次
            case 2:
                cron = "0 0 1  1 * ?";
                break;
            //每个季度的第一个月的一号的1:00:00 执行一次
            case 3:
                cron = "0 0 1  1 1,4,7,10 ?";
                break;
            //每年一月的一号的1:00:00 执行一次
            case 4:
                cron = "0 0 1 1 1 ?";
                break;
            //默认每周
            default:
                cron = "0 0 1 ? * MON";
        }
        conditionLadderCommission.setCron(cron);
    }

    /**
     * 设置职位名称
     * @param conditionLadderCommission
     */
    private void setPositionNames(ConditionLadderCommission conditionLadderCommission) {
        //查询职位名称
        String positionIds = conditionLadderCommission.getPositionIds();

        List<ListBox> listBoxes = listBoxMapper.selectList(Wrappers.<ListBox>query().lambda()
                .in(ListBox::getId, Splitter.on(CommonConstants.SEPARATOR).splitToList(positionIds)));

        //用逗号隔开放到数据库里
        conditionLadderCommission.setPositionNames(Joiner.on(CommonConstants.SEPARATOR)
                .join(listBoxes.stream().map(ListBox::getName).collect(Collectors.toList())));
    }
}
