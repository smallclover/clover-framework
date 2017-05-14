package org.clover4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * @author smallclover
 * @create 2017-01-17
 * @since 1.0.0
 */
public abstract class AspectProxy implements Proxy{

    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    /**
     * 切面的相关逻辑
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        
        begin();

        try {
            if (intercept(cls, method, params)){
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            logger.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{
        return true;
    }

    /**
     * 在目标方法调用前执行
     * @param cls
     * @param method
     * @param params
     * @throws Throwable
     */
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable{

    }

    /**
     * 在目标方法调用后执行
     * @param cls
     * @param method
     * @param params
     * @param result
     * @throws Throwable
     */
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable{
    }

    /**
     * 在抛出异常时执行
     * @param cls
     * @param method
     * @param params
     * @param e
     * @throws Throwable
     */
    public void error(Class<?> cls, Method method, Object[] params, Throwable e) throws Throwable{
    }

    /**
     * 在进入方法时执行
     */
    public void begin() {
    }

    /**
     * 在退出方法时执行
     */
    public void end(){

    }

}
