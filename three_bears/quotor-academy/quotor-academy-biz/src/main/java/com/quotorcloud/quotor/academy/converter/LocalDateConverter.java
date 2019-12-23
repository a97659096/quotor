package com.quotorcloud.quotor.academy.converter;

import com.quotorcloud.quotor.common.core.util.ComUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        if(ComUtil.isEmpty(s)){
            return null;
        }else {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

}
