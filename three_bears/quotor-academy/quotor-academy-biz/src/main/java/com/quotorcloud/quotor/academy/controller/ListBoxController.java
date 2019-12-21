package com.quotorcloud.quotor.academy.controller;

import com.quotorcloud.quotor.academy.service.ListBoxService;
import com.quotorcloud.quotor.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list/box")
public class ListBoxController {

    @Autowired
    private ListBoxService listBoxService;

    @GetMapping("check")
    public R checkListBox(String name, String module, String tag){
        listBoxService.checkListBox(name, module, tag);
        return R.ok();
    }

    @GetMapping
    public R getListBox(String module, String tag){
        return R.ok(listBoxService.listBox(module, tag));
    }
}
