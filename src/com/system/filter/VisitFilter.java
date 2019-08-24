package com.system.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * -----非法访问拦截器-----
 * 亲测，没个卵用，瞎拦截，暂时注掉部分代码废置处理
 * @Author: 枠成
 * @Data:2019-08-23
 * @Description:com.system.filter
 * @version:1.0
 */
@WebFilter("/add.jsp")
public class VisitFilter extends HttpServlet implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(true);

        System.out.println("-----非法访问拦截器-----");

        //获取登录时存放的session
        String id = (String) session.getAttribute("id");

        //获取地址栏的url
        String url = request.getRequestURI();
        //未登陆、当前不是登陆的Jsp、也不是登陆的.do
//        if (!url.contains("index.jsp") && id == null && !url.equals("/index") && !url.contains("method")) {
//            //跳转至指定界面
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//            response.setHeader("Cache-Control", "no-store");
//            response.setDateHeader("Expires", 0);
//            response.setHeader("Pragma", "no-cache");
//        } else {
//            filterChain.doFilter(request, response);
//        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
