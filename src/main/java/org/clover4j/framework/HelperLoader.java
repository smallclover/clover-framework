package org.clover4j.framework;

import org.clover4j.framework.annoation.Controller;
import org.clover4j.framework.helper.*;
import org.clover4j.framework.util.ClassUtil;

/**
 * 加载相应的Helper类
 * @author smallclover
 * @create 2017-01-06
 * @since 1.0.0
 */
public final class HelperLoader {

    public static void init(){

        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class, /**@since 2.0.0 注意需要在IocHelper之前加载**/
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
