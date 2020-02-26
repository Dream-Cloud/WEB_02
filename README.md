# 1、复习

## 1 Servlet的生命周期

init(ServletConfig  config) 创建并初始化servlet 

load-on-startup

​	\>=0  随着servelt的装载而进行初始化  数字越小 初始化越早

​	<0 则是在第一次请求时进行初始化

service(ServletRequest req ,ServletResponse resp)  serlvet的接受请求并处理请求 返回响应

destroy(); 销毁 当servlet从容器中卸载时  执行 后续的清理工作

## 2 Servlet的核心API

ServletConfig  封装了当前servlet的配置信息 

ServletContext 代表整个web应用的全部信息  可以实现servelt之间的数据共享 

## 3 四大域对象：

pageContext

ServletRequst

HttpSession

ServletContext

域对象的公有方法：

 setAttribute(String key ,Object value);

 getAttribute(String key);

 removeAttribute(String key);

# 2、servlet请求

## 2.1 请求概述

HttpServletRequest  接受请求的 

谁可以发送请求：

1. 通过浏览器的地址栏发送请求  可以发送get请求

2. 超链接发送请求 请求的地址href  只能发送get请求

3. form表单  可以通过action来发送请求 不仅可以发送get请求还可以发送post请求  根据method属性来指定请求的方式

get的参数传递是通过路径后?参数名=参数值&参数名=参数值

post的参数通过请求体来传递参数的

发送get请求

http://localhost:8080/demo1?username=admin

## 2.2 requst相关方法：

- **int getContentLength()**：获取请求体的字节数，GET请求没有请求体，没有请求体返回-1；
- **String getContentType()**：获取请求类型，如果请求是GET，那么这个方法返回null；如果是POST请求，那么默认为application/x-www-form-urlencoded，表示请求体内容使用了URL编码；
- **String getMethod()**：返回请求方法，例如：GET
- **Locale getLocale()**：返回当前客户端浏览器的Locale。java.util.Locale表示国家和言语，这个东西在国际化中很有用；
- **String getCharacterEncoding()**：获取请求编码，如果没有setCharacterEncoding()，那么返回null，表示使用ISO-8859-1编码；
- **void setCharacterEncoding(String code)**：设置请求编码，只对请求体有效！注意，对于GET而言，没有请求体！！！所以此方法只能对POST请求中的参数有效！
- **String getContextPath()**：返回上下文路径，例如：/hello
- **String getQueryString()**：返回请求URL中的参数，例如：name=zhangSan
- **String getRequestURI()**：返回请求URI路径，例如：/hello/oneServlet
- **StringBuffer getRequestURL()**：返回请求URL路径，例如：http://localhost/hello/oneServlet，即返回除了参数以外的路径信息；
- **String getServletPath()**：返回Servlet路径，例如：/oneServlet
- **String getRemoteAddr()**：返回当前客户端的IP地址；
- **String getRemoteHost()**：返回当前客户端的主机名，但这个方法的实现还是获取IP地址；
- **String getScheme()**：返回请求协议，例如：http；
- **String getServerName()**：返回主机名，例如：localhost
- **int getServerPort()**：返回服务器端口号，例如：8080

## 2.3 GET请求和POST请求的区别：

- GET请求：

- - 请求参数会在浏览器的地址栏中显示，所以不安全；
  - 请求参数长度限制长度在1K之内；
  - GET请求没有请求体，无法通过request.setCharacterEncoding()来设置参数的编码；

- POST请求：

![img](E:\YouDaoYun\m15234512314@163.com\84186bd903a34354a626294e20eee925\clipboard.png)

- - 请求参数不会显示浏览器的地址栏，相对安全；
  - 请求参数长度没有限制；

## 2.4 解决post请求的中文乱码：

**方式一：采用解码编码的方式来解决**

```java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    //对获取到的数据进行节码 得到字节数组
    byte[] usernameBytes = username.getBytes("iso-8859-1");
    // 使用新的字符集重新编码
    String name = new String(usernameBytes,"UTF-8");
    System.out.println(name);
    String key = req.getParameter("key");
    System.out.println(key);
}
```

**方式二：**

```java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //设定接收请求所采用的字符集
    req.setCharacterEncoding("utf-8");
    String username = req.getParameter("username");
    System.out.println(username);
    String key = req.getParameter("key");
    System.out.println(key);
}
```

Get

**通用解决方式：**

配置tomcat的字符集 conf/serve.xml

```xml
 <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"  URIEncoding="UTF-8"/>
```

# 3、Response 响应

## 3.1 请求转发和重定向

### 3.1.1 请求转发：

1 浏览器向服务器端只发送了一次请求

2 浏览器的地址栏不会发生变化

3 不能转发到外部资源  只能转发内部资源

4 所以转发整个的过程都是一个请求  此时的request域的数据是可以在转发的servlet之间共享的  对于转发的serlvet来说是同一个请求

5 转发不仅可以转发到一个servlet 也可以转发到一个页面

路径前边 的第一个/表示根路径  就是我们项目结构中的web目录

```java
//请求转发 
req.getRequestDispatcher("/home.html").forward(req,resp);
req.getRequestDispatcher("/WEB-INF/success.html").forward(req,resp);
```

### 3.1.2 重定向

1 重定向向服务器端发送了两次请求 第一次请求可以是get也可以是post  但是第二次请求肯定是get

2 重定向时 地址栏会发生变化 显示重定向的地址

3 可以重定向到外部资源 可以重定向到内部资源

4 在整个请求 中发生了两次请求  产生了两个request对象 不能实现数据共享

5 也可以重定向到页面  和serlvet

## 3.1.3 转发和重定向的使用的选择：

1. **不同servlet之间的调用  使用转发 **
2. **如果要跳转页面 则使用重定向**
