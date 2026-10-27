package com.foodorder.servlet.admin;

import com.foodorder.dao.OrderDAO;
import com.foodorder.entity.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/admin/orderServlet")
public class AdminOrderServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "detail":
                handleDetail(req, resp);
                break;
                
            case "updateStatus":
                handleUpdateStatus(req, resp);
                break;
                
            case "list":
            	
            default:
                req.setAttribute("orders", orderDAO.findAll());
                
                req.getRequestDispatcher("/pages/admin/orderManage.jsp").forward(req, resp);
                
                break;
        }
    }

    private void handleDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        Integer id = parseInt(req.getParameter("id"));
        
        Order order = id == null ? null : orderDAO.findById(id);
        
        req.setAttribute("orders", orderDAO.findAll());
        
        req.setAttribute("selectedOrder", order);
        
        req.getRequestDispatcher("/pages/admin/orderManage.jsp").forward(req, resp);
        
    }

    private void handleUpdateStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	
        Integer id = parseInt(req.getParameter("id"));
        
        Integer status = parseInt(req.getParameter("status"));
        
        if (id != null && status != null) {
        	
            orderDAO.updateStatus(id, status);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/orderServlet?action=detail&id=" + id);
    }

    private Integer parseInt(String s) {
    	
        if (s == null || s.trim().isEmpty()) return null;
        
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
