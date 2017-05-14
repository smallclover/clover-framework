package org.clover4j.framework;

import org.clover4j.framework.bean.Data;
import org.clover4j.framework.bean.Handler;
import org.clover4j.framework.bean.Param;
import org.clover4j.framework.bean.View;
import org.clover4j.framework.helper.BeanHelper;
import org.clover4j.framework.helper.ConfigHelper;
import org.clover4j.framework.helper.ControllerHelper;
import org.clover4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器，框架核心控制器
 * 基于servlet3.0
 * @author smallclover
 * @create 2017-01-08
 * @since 1.0.0
 */

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)//这里urlPatterns如果是/将会出现问题
public class DispatcherServlet extends HttpServlet{//这里可以做一些改进

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        //初始化框架核心类
        HelperLoader.init();
        //获取ServletContext对象(用于注册Servlet)
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //注意：没有设置请求编码，是否会出现乱码的问题。

        //获取请求方法和请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //String requestPath_test = req.getServletPath();//根据拦截的路径不同与getPathInfo方法是不同的。
        //getServletPath-"/*"    getPathInfo-"*.jsp"

        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

        if (handler != null){

            //获取Controller类及其Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();//获取页面中所有元素的名称

            //注意：这里没有考虑一个复选框，即一个名字包含多个值，此时需要使用数组来接受。*notice：bug
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                //需要考虑三种情况：1.值为null；2.只有一个值；3.有多个值
                //String[] paramValues = req.getParameterValues(paramName);
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            //处理post请求
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)){
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)){
                    for (String param : params){
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);
            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = null;//是否会出现空指针异常？
            if (param.isEmpty()){
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            }else {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }
            //处理Action方法的返回值
            //返回View类型
            if (result instanceof View){
                //返回JSP页面
                View view = (View) result;
                handlerViewResult(view, req, resp);
            }else if (result instanceof Data){
                //返回JSON类型
                Data data = (Data) result;
                handlerDataResult(data, resp);
            }
        }
    }

    /**
     *转发返回jsp页面的请求
     * @param view
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     * @since 2.1.2
     */
    private void handlerViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                //是否有需要支持重定向即sendRedirect
            }
        }
    }

    /**
     * 转发返回json数据请求
     * @param data
     * @param resp
     * @throws IOException
     * @since 2.1.2
     */
    private void handlerDataResult(Data data, HttpServletResponse resp) throws IOException {
        Object model = data.getModel();
        if (model != null){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
