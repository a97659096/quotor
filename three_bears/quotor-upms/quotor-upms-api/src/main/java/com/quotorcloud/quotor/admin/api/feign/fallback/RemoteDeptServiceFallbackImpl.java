package com.quotorcloud.quotor.admin.api.feign.fallback;


import com.quotorcloud.quotor.admin.api.dto.DeptDTO;
import com.quotorcloud.quotor.admin.api.feign.RemoteDeptService;
import com.quotorcloud.quotor.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteDeptServiceFallbackImpl implements RemoteDeptService {
    @Setter
    private Throwable cause;

    @Override
    public R updateDept(DeptDTO deptDTO) {
        log.error("feign 修改部门信息失败", cause);
        return null;
    }

    @Override
    public R listDeptById(Integer id) {
        log.error("feign 查询部门信息失败", cause);
        return null;
    }

    @Override
    public R listDeptAll() {
        log.error("feign 查询部门信息失败", cause);
        return null;
    }
}
