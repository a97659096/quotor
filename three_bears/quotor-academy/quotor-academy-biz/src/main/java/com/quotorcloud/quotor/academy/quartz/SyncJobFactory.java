package com.quotorcloud.quotor.academy.quartz;

import com.quotorcloud.quotor.academy.api.entity.condition.ConditionLadderCommission;
import com.quotorcloud.quotor.academy.mapper.condition.ConditionLadderCommissionMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 同步任务工厂
 * version : 1.0
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class SyncJobFactory extends QuartzJobBean {

    @Autowired
    private ConditionLadderCommissionMapper conditionLadderCommissionMapper;

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(SyncJobFactory.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("SyncJobFactory execute");
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        ConditionLadderCommission scheduleJob = (ConditionLadderCommission) mergedJobDataMap.get(ConditionLadderCommission.JOB_PARAM_KEY);
        System.out.println("jobName:" + scheduleJob.getName() + "  " + scheduleJob);
        scheduleJob.setName("定制任务111");
        conditionLadderCommissionMapper.updateById(scheduleJob);
    }
}
