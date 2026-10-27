package com.foodorder.servlet;

import com.foodorder.dao.CategoryDAO;
import com.foodorder.dao.DishDAO;
import com.foodorder.entity.Dish;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/menuServlet")
public class MenuServlet extends HttpServlet {

    private final DishDAO dishDAO = new DishDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer categoryId = parseInt(req.getParameter("categoryId"));

        List<Dish> dishes = (categoryId == null) ? dishDAO.findAll()  : dishDAO.findByCategory(categoryId);

        req.setAttribute("dishes", dishes);
        
        req.setAttribute("categories", categoryDAO.findAll());
        
        
        req.setAttribute("selectedCategoryId", categoryId);
        
        req.getRequestDispatcher("/pages/user/menu.jsp").forward(req, resp);
        
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