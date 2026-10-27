package com.foodorder.servlet.admin;

import com.foodorder.dao.DishDAO;
import com.foodorder.dao.OrderDAO;
import com.foodorder.dao.UserDAO;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/admin/dashboardServlet")
public class DashboardServlet extends HttpServlet {

    private final DishDAO dishDAO = new DishDAO();
    
    private final UserDAO userDAO = new UserDAO();
    
    private final OrderDAO orderDAO = new OrderDAO();
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        req.setAttribute("dishCount", dishDAO.countAll());
        
        req.setAttribute("userCount", userDAO.countAll());
        
        req.setAttribute("todayOrderCount", orderDAO.countToday());
        
        req.getRequestDispatcher("/pages/admin/dashboard.jsp").forward(req, resp);
    }
}
