package com.foodorder.servlet;

import com.foodorder.dao.UserDAO;
import com.foodorder.entity.User;
import com.foodorder.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String target = req.getParameter("target"); 

        User user = null;
        if (username != null && password != null) {
        	
            User foundUser = userDAO.findByUsername(username.trim());
            
         
            if (foundUser != null && PasswordUtil.matches(password, foundUser.getPassword())) {
            	
                user = foundUser;
                
            }
        }

        boolean isAdminAttempt = "admin".equals(target);

        if (user == null || (isAdminAttempt && (user.getRole() == null || user.getRole() != 1))) {
        	
            if (isAdminAttempt) {
            	
                resp.sendRedirect(req.getContextPath() + "/pages/admin/adminLogin.jsp?error=1");
                
            } else {
            	
                resp.sendRedirect(req.getContextPath() + "/pages/user/login.jsp?error=1");
                
            }
            return;
        }

        HttpSession session = req.getSession(true);
        
        session.setAttribute("loginUser", user);
        
        session.setMaxInactiveInterval(30 * 60); 

         
        if (user.getRole() != null && user.getRole() == 1) {
        	
            resp.sendRedirect(req.getContextPath() + "/admin/dashboardServlet");
            
        } else {
        	
            resp.sendRedirect(req.getContextPath() + "/pages/user/index.jsp");
            
        }
       
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    
            throws ServletException, IOException {
    	
        resp.sendRedirect(req.getContextPath() + "/pages/user/login.jsp");
        
    }
}
