package com.quotorcloud.quotor.academy.event;

import com.quotorcloud.quotor.academy.api.entity.ListBox;
import org.springframework.context.ApplicationEvent;

public class ListBoxEvent extends ApplicationEvent {

    public ListBoxEvent(ListBox source) {
        super(source);
    }

}
