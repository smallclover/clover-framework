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

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
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

        return null;
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{

        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable{

    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable{
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) throws Throwable{
    }
    public void begin() {
    }

    public void end(){

    }


    public static void main(String[] args) {
        int i = 100;
        int n = 1;
        while ( n <= i){
            if (n % 3 == 0){
                System.out.println("three");
            }

            System.out.println(n);
            n++;
        }
    }
}
