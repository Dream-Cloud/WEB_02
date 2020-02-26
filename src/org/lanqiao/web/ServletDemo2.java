package org.lanqiao.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletDemo2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设定接受请求所采用的字符集
//        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        System.out.println(username);
        //对得到的数据进行解码 得到字节数组
        byte[] usernameBytes = username.getBytes("iso-8859-1");
        //使用新的字符集重新编码
        String name = new String(usernameBytes,"utf-8");
        System.out.println(name);



        //通过response响应客户端 常用来设置响应头 设置响应体

        PrintWriter out = resp.getWriter();
        //设置响应内容类型
        resp.setContentType("text/html");
        //设置响应的字符集
        resp.setCharacterEncoding("UTF-8");
        //一步到位 合并写法
        //resp.setContentType("text/html;charset=utf-8");
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>欢迎页</title>\n" +
                "</head>");
        out.println("<body>");
        out.println("欢迎您:" + username);
        out.println("</body>");
        out.println("</html>");
        //刷新缓冲区
        resp.flushBuffer();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
