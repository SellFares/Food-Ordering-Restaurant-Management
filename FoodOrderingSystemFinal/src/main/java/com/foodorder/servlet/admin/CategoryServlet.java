package com.foodorder.servlet.admin;

import com.foodorder.dao.CategoryDAO;
import com.foodorder.entity.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/categoryServlet")
public class CategoryServlet extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        String action = req.getParameter("action");
        
        if ("delete".equals(action)) {
        	
            Integer id = parseInt(req.getParameter("id"));
            
            String error = null;
            
            if (id != null && !categoryDAO.delete(id)) {
            	
                error = "Cannot delete: this category still has dishes assigned to it.";
                
            }
            
            showList(req, resp, error);
            
            return;
            
        }
        
        showList(req, resp, null);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        String action = req.getParameter("action");
        
        String name = req.getParameter("name");
        

        if (name == null || name.trim().isEmpty()) {
        	
            showList(req, resp, "Category name is required.");
            
            
            return;
        }

        Category category = new Category();
        
        
        category.setId(parseInt(req.getParameter("id")));
        
        
        category.setName(name.trim());
        

        boolean ok;
        
        
        if ("update".equals(action) && category.getId() != null) {
        	
            ok = categoryDAO.update(category);
            
        } else {
        	
            ok = categoryDAO.insert(category);
            
        }

        if (!ok) {
        	
            showList(req, resp, "Could not save the category, please try again.");
            return;
            
        }
        
        resp.sendRedirect(req.getContextPath() + "/admin/categoryServlet");
        
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
    	
        java.util.List<Category> categories = categoryDAO.findAll();
        
        for (Category c : categories) {
        	
            c.setDishCount(categoryDAO.countDishes(c.getId()));
            
        }
        
        req.setAttribute("categories", categories);
        
        if (error != null) req.setAttribute("error", error);
        
        req.getRequestDispatcher("/pages/admin/categoryManage.jsp").forward(req, resp);
        
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
