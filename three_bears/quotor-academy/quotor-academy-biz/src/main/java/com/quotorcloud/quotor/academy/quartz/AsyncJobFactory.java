package com.quotorcloud.quotor.academy.quartz;

import com.quotorcloud.quotor.academy.api.entity.condition.ConditionLadderCommission;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * author : fengjing
 * createTime : 2016-08-04
 * description : 异步任务工厂
 * version : 1.0
 */
public class AsyncJobFactory extends QuartzJobBean {

    /* 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(AsyncJobFactory.class);
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("AsyncJobFactory execute");
        ConditionLadderCommission scheduleJob = (ConditionLadderCommission) context.getMergedJobDataMap().get(ConditionLadderCommission.JOB_PARAM_KEY);
        System.out.println("jobName:" + scheduleJob.getName() + "  " + scheduleJob);
    }
}
