package com.quotorcloud.quotor.academy.service.message;

import com.quotorcloud.quotor.academy.api.entity.message.MessageMass;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quotorcloud.quotor.academy.api.vo.message.MessageMassVO;

/**
 * <p>
 * 短信群发记录表 服务类
 * </p>
 *
 * @author tianshihao
 * @since 2019-12-06
 */
public interface MessageMassService extends IService<MessageMass> {

    /**
     * 群发短信
     * @param messageMassVO
     * @return
     */
    Boolean saveMessageMassRecord(MessageMassVO messageMassVO);

}
