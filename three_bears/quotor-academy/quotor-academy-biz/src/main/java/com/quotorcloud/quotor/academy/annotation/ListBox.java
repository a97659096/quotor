package com.quotorcloud.quotor.academy.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ListBox {

    String module() default  "";

    String tag() default "";
}
