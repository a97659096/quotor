package com.quotorcloud.quotor.academy.service;

import java.util.List;

public interface ListBoxService {

    void checkListBox(String name, String module, String tag);

    List<String> listBox(String module, String tag);
}
