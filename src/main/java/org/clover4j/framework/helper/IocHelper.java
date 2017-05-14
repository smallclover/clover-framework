package org.clover4j.framework.helper;

import org.clover4j.framework.annoation.Inject;
import org.clover4j.framework.util.ArrayUtil;
import org.clover4j.framework.util.CollectionUtil;
import org.clover4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入
 * @author smallclover
 * @create 2017-01-04
 * @since 1.0.0
 */
public class IocHelper {
    //bug：暂时无法实现为父类注入子类的实例
    static {

        //获取所有的Bean类与Bean实例之间的映射关系（简称Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if (CollectionUtil.isNotEmpty(beanMap)){

            //遍历Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
                //从Bean Map中获得 Bean类和Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                //获取Bean类定义的所有成员变量（简称Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();

                if (ArrayUtil.isNotEmpty(beanFields)){
                    //遍历Bean Field
                    for (Field beanField: beanFields){
                        //判断当前Bean field是否带Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)){
                            //在Bean Map中获取Bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null){
                                //通过反射来设置Field的实例
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
