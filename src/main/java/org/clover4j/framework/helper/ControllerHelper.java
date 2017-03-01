package org.clover4j.framework.helper;

import org.clover4j.framework.annoation.Action;
import org.clover4j.framework.bean.Handler;
import org.clover4j.framework.bean.Request;
import org.clover4j.framework.util.ArrayUtil;
import org.clover4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 管理@Controller注解的控制器
 *
 * @author smallclover
 * @create 2017-01-04
 * @since 1.0.0
 */
public final class ControllerHelper {

    //存放Request和Handler之间的映射关系
    public static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        //获取所有Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtil.isNotEmpty(controllerClassSet)){
            //遍历这些Controller类
            for (Class<?> controllerClass : controllerClassSet){
                //获取Controller类中的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)){
                    //遍历这些Controller类中的方法
                    for (Method method : methods){
                        //判断当前方法是否带有Action注解
                        if (method.isAnnotationPresent(Action.class)){
                            //从Action注解中获取URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();

                            //验证URL映射规则
                            if (mapping.matches("\\w+:/\\w*")){//正则表达式

                                // \w匹配字母或数字或下划线或汉字
                                // + 重复一次或更多次
                                // * 重复零次或更多次
                                String[] array = mapping.split(":");

                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    //获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);

                                    //初始化Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
