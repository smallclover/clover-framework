package org.clover4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 * @author smallclover
 * @create 2017-01-17
 * @since 2.0.0
 */
public class ProxyManager {

    /**
     * CGLib Enhance#create
     * @param targetClass 需要被代理的目标类
     * @param proxyList 代理集合
     * @param <T>
     * @return
     */
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
        return (T)Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod,
                        methodProxy, methodParams, proxyList).doProxyChain();
            }
        });
    }

}
