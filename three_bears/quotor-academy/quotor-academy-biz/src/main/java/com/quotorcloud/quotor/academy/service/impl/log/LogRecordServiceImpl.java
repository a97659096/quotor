package com.quotorcloud.quotor.academy.service.impl.log;

import com.quotorcloud.quotor.academy.api.entity.log.LogRecord;
import com.quotorcloud.quotor.academy.mapper.log.LogRecordMapper;
import com.quotorcloud.quotor.academy.service.log.LogRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-22
 */
@Service
public class LogRecordServiceImpl extends ServiceImpl<LogRecordMapper, LogRecord> implements LogRecordService {

}
