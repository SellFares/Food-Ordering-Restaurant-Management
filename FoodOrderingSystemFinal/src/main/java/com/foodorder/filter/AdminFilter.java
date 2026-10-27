package com.foodorder.filter;

import com.foodorder.entity.User;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        if (uri.endsWith("adminLogin.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User loginUser = (session == null) ? null : (User) session.getAttribute("loginUser");

        if (loginUser == null || loginUser.getRole() == null || loginUser.getRole() != 1) {
        	
        	
        	
            resp.sendRedirect(req.getContextPath() + "/pages/admin/adminLogin.jsp");
            
            
            return;
        }

        chain.doFilter(request, response);
    }
}
