package com.quotorcloud.quotor.admin.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

public class MultConverter implements Converter<String, MultipartFile> {

    @Override
    public MultipartFile convert(String s) {
        if("null".equals(s)){
            return null;
        }
        return null;
    }
}
