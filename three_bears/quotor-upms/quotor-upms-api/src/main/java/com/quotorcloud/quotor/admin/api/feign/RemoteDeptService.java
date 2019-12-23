package com.quotorcloud.quotor.admin.api.feign;


import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.feign.factory.RemoteTokenServiceFallbackFactory;
import com.quotorcloud.quotor.common.core.constant.ServiceNameConstants;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.UMPS_SERVICE, fallbackFactory = RemoteTokenServiceFallbackFactory.class)
public interface RemoteDeptService {

    @PutMapping("/dept")
    R updateDept(DeptDTO deptDTO);

    @GetMapping("/dept/list/{id}")
    R listDeptById(@PathVariable(value = "id") Integer id);

    @GetMapping("/dept/list-dept")
    R listDeptAll();
}
