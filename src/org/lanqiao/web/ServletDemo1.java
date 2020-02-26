package org.lanqiao.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDemo1 extends HttpServlet {
    //需求：统计当前访问应用的应用数量
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //编写业务核心代码
        ServletContext application = req.getServletContext();
        Integer online_num = (Integer) application.getAttribute("online_num");
        if (online_num == null) {
            online_num = 1;
        }else{
            Integer online_int = online_num;
            online_int++;
            online_num = online_int;
        }
        application.setAttribute("online_num",online_num);
        System.out.println("您是访问该应用的第" + online_num + "个用户");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
