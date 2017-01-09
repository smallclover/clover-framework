package org.clover4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 * @author smallclover
 * @create 2017-01-04
 * @since 1.0.0
 */
public class Handler {

    /**
     * Controller
     */
    private Class<?> controllerClass;

    /**
     * Action 方法
     */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass(){
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
