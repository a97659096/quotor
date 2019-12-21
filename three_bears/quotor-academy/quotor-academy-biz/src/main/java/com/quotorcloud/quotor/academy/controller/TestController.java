package com.quotorcloud.quotor.academy.controller;


import com.quotorcloud.quotor.academy.service.TestDataService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestDataService testDataService;

    @GetMapping("product")
    public R listProduct(){
        return R.ok(testDataService.listProduct());
    }

    @GetMapping("shop")
    public R listShop(){
        return R.ok(testDataService.listShop());
    }
}
