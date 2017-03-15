package org.clover4j.framework.annoation;

import java.lang.annotation.*;

/**
 * 切面注解
 * @author smallclover
 * @create 2017-01-16
 * @since 2.0.0
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     * @return
     */
    Class<? extends Annotation> value();
}
