package org.clover4j.framework.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Action方法直接
 * @author smallclover
 * @create 2017-01-04
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /**
     * 请求类型路径
     * @return
     */
    String value();
}
