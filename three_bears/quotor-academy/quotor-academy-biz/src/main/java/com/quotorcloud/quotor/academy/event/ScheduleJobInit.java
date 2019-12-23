package com.quotorcloud.quotor.academy.event;

import com.quotorcloud.quotor.academy.service.condition.ConditionLadderCommissionService;
import com.quotorcloud.quotor.academy.service.impl.condition.ConditionLadderCommissionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ScheduleJobInit {
    /** 定时任务service */
    @Autowired
    private ConditionLadderCommissionServiceImpl scheduleJobService;

    /**
     * 项目启动时初始化
     */
//    @PostConstruct
//    public void init() {
//
//        if (log.isInfoEnabled()) {
//            log.info("init");
//        }
//
//        scheduleJobService.initScheduleJob();
//
//        if (log.isInfoEnabled()) {
//            log.info("end");
//        }
//    }

}
