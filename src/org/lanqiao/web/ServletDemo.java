package org.lanqiao.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//继承HttpServlet
public class ServletDemo extends HttpServlet {
    //重写doGet和doPost方法
    //doGet只接受get请求  doPost只接受post请求
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //编写业务核心代码
        //获取ServletConfig对象 通过servletConfig获取相关配置
        ServletConfig config = this.getServletConfig();
        String name = config.getServletName();
        String param = config.getInitParameter("username");
        System.out.println(name+"-----"+param);

        //获取ServletContext对象
        //param内容一经确定 不可修改
        ServletContext application = this.getServletContext();
        String param2 = application.getInitParameter("app");
        System.out.println(param2);
        //attribute中内容可以对此提交  新值替换旧值
        application.setAttribute("school","中北大学");
        String school = (String) application.getAttribute("school");
        System.out.println(school);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
