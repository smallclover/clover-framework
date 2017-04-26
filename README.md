# clover-framework
一个简单的MVC框架
目前2.1.1版本项目代码参考开源中国@黄勇的Smart框架

## *Project Environment*
    OS: Window 10
    IDE: Intellij IDEA 2016.3.5
    Java Version: JDK 1.8
    Tomcat Version: Tomcat 8.0
    Maven Version: Maven 3.3.9
## *介绍&使用方法*

+ **DispatcherServlet** 框架的核心控制转发器 
+ **HelperLoader** 初始化加载器，加载框架必须的组件，由DispatcherServlet调用
+ **Controller** 注解，MVC设计模式中的C（Controller）层
+ **Action** 注解，用于标注Controller下的方法，该注解需要一个参数如：@Action("get/home")表示以get请求的方式访问路径home
+ **Service** 注解，服务层
+ **Inject** 注解，用于依赖注入
+ **Aspect** 注解，用于进行增强，该注解需要一个参数如：@Aspect(Controller.class)表示，增强所有的用@Controller标注的类，该注解和AspectProxy一起使用。

## *注意事项*
1. ~~@Action注解的函数需要带有参数**Param**~~
