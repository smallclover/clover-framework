package org.clover4j.framework.helper;

import org.clover4j.framework.annoation.Controller;
import org.clover4j.framework.annoation.Service;
import org.clover4j.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author smallclover
 * @create 2017-01-04
 */
public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    /**
     * 定义类集合（用于存放所加载的类）
     */
    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包下的所有类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包下所有Service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for (Class<?> cls: CLASS_SET){
            if (cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * 获取应用包下所有Controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();

        for (Class<?> cls: CLASS_SET){
            if (cls.isAnnotationPresent(Controller.class)){
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * 获取应用包下所有Bean类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());

        return beanClassSet;
    }
}
