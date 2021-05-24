package com.github.menglim.dbutils.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbField {

    String value() default "";

    boolean primaryKey() default false;

    boolean insertField() default true;

    boolean updateField() default true;

    boolean ignore() default false;

    String defaultValueIfNull() default "";
}
