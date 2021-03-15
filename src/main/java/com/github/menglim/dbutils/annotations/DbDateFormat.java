package com.github.menglim.dbutils.annotations;

import com.github.menglim.mutils.Constants;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbDateFormat {

    String toFormatDate() default "";

    Constants.FormatDate fromFormatDate() default Constants.FormatDate.DDMMYYYY;
}
