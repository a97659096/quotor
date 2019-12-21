package com.quotorcloud.quotor.academy.util;

import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.core.util.MethodUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import com.quotorcloud.quotor.common.security.util.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
public class ShopSetterUtil {

    public void shopSetter(Object o, String shopId){
        //获取用户信息并赋值
        if(!ComUtil.isEmpty(shopId)){

        }else {
            QuotorUser user = SecurityUtils.getUser();
            if(ComUtil.isEmpty(user) || ComUtil.isEmpty(user.getDeptId())){
                return;
            }
            try {
                if(!ComUtil.isEmpty(o.getClass().getDeclaredField("shopId"))){
                    MethodUtil.setValue(o, o.getClass(), "shopId", String.class, String.valueOf(user.getDeptId()));
                }
                if(!ComUtil.isEmpty(o.getClass().getDeclaredField("shopName"))){
                    MethodUtil.setValue(o, o.getClass(), "shopName", String.class, user.getDeptName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
