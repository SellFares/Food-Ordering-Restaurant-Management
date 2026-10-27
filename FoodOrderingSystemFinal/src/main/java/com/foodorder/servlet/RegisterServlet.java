package com.foodorder.servlet;

import com.foodorder.dao.UserDAO;
import com.foodorder.entity.User;
import com.foodorder.util.PasswordUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        
        String email = req.getParameter("email");
        
        String phone = req.getParameter("phone");
        
        String password = req.getParameter("password");
        

        String error = null;

        if (username == null || username.trim().length() < 3) {
        	
            error = "Username must be at least 3 characters.";
            
        } else if (password == null || password.length() < 6) {
        	
            error = "Password must be at least 6 characters.";
            
        } else if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$")) {
        	
            error = "Please enter a valid email address.";
            
        } else if (phone == null || !phone.matches("^[0-9+\\-\\s]{7,20}$")) {
        	
            error = "Please enter a valid phone number.";
            
        } else if (userDAO.existsByUsername(username.trim())) {
        	
            error = "This username is already taken.";
            
        } else if (userDAO.existsByEmail(email.trim())) {
        	
            error = "This email is already registered.";
            
        }

        if (error != null) {
        	
        	
            req.setAttribute("error", error);
            
            req.setAttribute("username", username);
            
            req.setAttribute("email", email);
            
            req.setAttribute("phone", phone);
            
            
            RequestDispatcher rd = req.getRequestDispatcher("/pages/user/register.jsp");
            
            rd.forward(req, resp);
            return;
        }

        User user = new User();
        
        user.setUsername(username.trim());
        
        user.setPassword(PasswordUtil.hash(password));
        
        user.setEmail(email.trim());
        
        user.setPhone(phone.trim());
        

        boolean success = userDAO.insert(user);

        if (success) {
        	
            resp.sendRedirect(req.getContextPath() + "/pages/user/login.jsp?registered=1");
            
            
        } else {
        	
        	
            req.setAttribute("error", "Registration failed, please try again later.");
            req.getRequestDispatcher("/pages/user/register.jsp").forward(req, resp);
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/pages/user/register.jsp");
    }
}
