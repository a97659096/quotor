package com.quotorcloud.quotor.academy.api.feign;


import com.quotorcloud.quotor.common.core.constant.ServiceNameConstants;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "listBoxFeign", value = ServiceNameConstants.ACAD_SERVICE)
public interface ListBoxFeign {

    /**
     * 检查数据是否存在,存在不进行操作，不存在进行增加窜哦做
     * @param name
     * @param module
     * @param tag
     * @return
     */
    @GetMapping("list/box/check")
    R checkListBox(String name, String module, String tag);

}
