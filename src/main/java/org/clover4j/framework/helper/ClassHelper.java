package org.clover4j.framework.helper;

import org.clover4j.framework.annoation.Controller;
import org.clover4j.framework.annoation.Service;
import org.clover4j.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author smallclover
 * @create 2017-01-04
 * @since 1.0.0
 */
public final class ClassHelper {
    //1.代码略微重复 2.getClassSet和getBeanClassSet意义不明

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
     * 获取应用包下所有包含@Service注解的服务层类
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
     * 获取应用包下所有包含@Controller注解的控制层类
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
     * 获取应用包下所有Bean类，即标有@Service和@Controller的类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());

        return beanClassSet;
    }

    /**
     * 获得应用包下某父类（或接口）的所有子类（或实现类）
     * @return
     * @since 2.0.0
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET){
            //Class.isAssignableFrom()是用来判断一个类Class1和另一个类Class2是否相同或是另一个类的子类或接口。
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * 获取某应用包下带有某注解的所有类
     * @param annotationClass
     * @return
     * @since 2.0.0
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET){
            if (cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }

        return classSet;
    }
}
