package com.github.menglim.dbutils.annotations;


import com.github.menglim.mutils.CoreConstants;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbDateFormat {

    String toFormatDate() default "";

    CoreConstants.FormatDate fromFormatDate() default CoreConstants.FormatDate.DDMMYYYY;

    String fromDateSeparator() default "-";
}
