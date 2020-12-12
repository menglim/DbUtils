package com.github.menglim.dbutils.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbDataSource {
    String schema() default "";

    int connectionIndex() default 0;
}
