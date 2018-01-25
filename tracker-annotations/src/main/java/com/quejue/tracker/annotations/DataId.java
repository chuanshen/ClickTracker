package com.quejue.tracker.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by chuan.shen on 2018/1/23.
 */
@Retention(SOURCE)
@Target({FIELD})
public @interface DataId {
    int[] value() default {};
}
