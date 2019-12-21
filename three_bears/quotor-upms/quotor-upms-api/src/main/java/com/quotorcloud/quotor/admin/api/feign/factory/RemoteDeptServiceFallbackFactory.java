package com.quotorcloud.quotor.admin.api.feign.factory;

import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.admin.api.feign.fallback.RemoteDeptServiceFallbackImpl;
import com.quotorcloud.quotor.admin.api.feign.fallback.RemoteLogServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteDeptServiceFallbackFactory implements FallbackFactory<RemoteDeptService> {
    @Override
    public RemoteDeptService create(Throwable throwable) {
        RemoteDeptServiceFallbackImpl remoteDeptServiceFallback = new RemoteDeptServiceFallbackImpl();
        remoteDeptServiceFallback.setCause(throwable);
        return remoteDeptServiceFallback;
    }
}
