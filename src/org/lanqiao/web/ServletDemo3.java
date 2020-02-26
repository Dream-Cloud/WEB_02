package org.lanqiao.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDemo3 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //请求转发
        //1.浏览器向服务器只发送了一次请求
        //2.浏览器的地址栏不会发生变化
        //3.不能转发到外部资源，只能转发内部资源
        //4.所以转发的整个过程都是一个请求 此时request域的数据是可以在转发的servlet之间共享的 对于转发的servlet来说就是同一个请求
        //5.转发不仅可以转发到一个servlet 还可以转发到一个页面 路径前面的第一个/表示根路径 就是我项目结构中的web目录的
        req.getRequestDispatcher("/demo2").forward(req, resp);
        //重定向
        //1.浏览器向服务器发送了两次请求第一次请求可以说get也可以是post但第二次肯定是get
        //2.重定向时地址会发生变化显示重定向的地址
        //3.可以重定向到外部资源也可以重定向到内部资源
        //4.在整个请求中发生了两次请求产生了两个request对象 不能实现数据共享
        //5.也可以重定向到页面和servlet
        resp.sendRedirect("/demo2");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
