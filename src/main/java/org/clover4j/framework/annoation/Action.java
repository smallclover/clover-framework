package org.clover4j.framework.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller#Action方法注解
 * @author smallclover
 * @create 2017-01-04
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /**
     * 请求的类型和路径
     * 样式如(get:/xxxx)
     * @return
     */
    String value();
}
