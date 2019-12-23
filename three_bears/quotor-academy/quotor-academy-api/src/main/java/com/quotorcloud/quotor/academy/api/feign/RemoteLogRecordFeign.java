package com.quotorcloud.quotor.academy.api.feign;


import com.quotorcloud.quotor.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "remoteLogRecordFeign", value = ServiceNameConstants.ACAD_SERVICE)
public interface RemoteLogRecordFeign {
}
