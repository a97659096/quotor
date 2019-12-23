package com.quotorcloud.quotor.common.core.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    // 传入包含所有用户的List和指定每个集合中有多少用户的值，该方法会返回一个装了指定数量用户信息的List的List。
    public static <T> List<List<T>> splitList(List<T>list, int pageSize) {
        int listSize = list.size();
        int page = (listSize + (pageSize - 1)) / pageSize;
        List<List<T>>listArray = new ArrayList<List<T>>();
        for (int i = 0; i<page; i++) {
            List<T> subList = new ArrayList<T>();
            for (int j = 0; j<listSize; j++) {
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                if (pageIndex == (i + 1)) {
                    subList.add(list.get(j));
                }
                if ((j + 1) == ((j + 1) * pageSize)) {
                    break;
                }
            }
            listArray.add(subList);
        }
        return listArray;
    }

}
