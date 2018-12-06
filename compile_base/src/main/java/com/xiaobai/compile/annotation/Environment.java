package com.xiaobai.compile.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Environment {

    String url();

    boolean isRelease() default false;


    String flavor() default "";

    String defaultFlavor() default "";

    String alias() default "";
}
