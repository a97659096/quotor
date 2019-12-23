//package com.quotorcloud.quotor.academy.event;
//
//import com.quotorcloud.quotor.academy.api.entity.ListBox;
//import com.quotorcloud.quotor.academy.api.feign.ListBoxFeign;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.annotation.Order;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@AllArgsConstructor
//@Component
//public class ListBoxListener {
//
//    private ListBoxFeign listBoxFeign;
//
//    @Async
//    @Order
//    @EventListener(ListBoxEvent.class)
//    public void saveListBox(ListBoxEvent listBoxEvent){
//        ListBox listBox = (ListBox) listBoxEvent.getSource();
//        listBoxFeign.checkListBox(listBox.getName(), listBox.getModule(), listBox.getTag());
//    }
//}
