package com.foodorder.servlet;

import com.foodorder.dao.DishDAO;
import com.foodorder.entity.CartItem;
import com.foodorder.entity.Dish;
import com.foodorder.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


@WebServlet("/cartServlet")
public class CartServlet extends HttpServlet {

    private final DishDAO dishDAO = new DishDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        		doPost(req, resp);
    	}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/pages/user/login.jsp?error=notLogin");
            return;
        }

        String action = req.getParameter("action");
        
        Integer dishId = parseInt(req.getParameter("dishId"));
        
        Integer qty = parseInt(req.getParameter("qty"));
        
        String error = null;

        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        
        if (cart == null) {
        	
            cart = new LinkedHashMap<>();
            
            session.setAttribute("cart", cart);
            
        }

        if ( "add".equals(action)) {
        	
            int quantityToAdd = (qty == null) ? 1 : qty;
            
            if (dishId == null || quantityToAdd <= 0) {
            	
                error = "Invalid quantity.";
                
            } else {
            	
                Dish dish = dishDAO.findById(dishId);
                
                if (dish == null) {
                	
                    error = "Dish not found.";
                    
                } else {
                	
                    CartItem existing = cart.get(dishId);
                    
                    if (existing != null) {
                    	
                        existing.setQuantity(existing.getQuantity() + quantityToAdd);
                        
                    } else {
                    	
                        cart.put(dishId, new CartItem(dish.getId(), dish.getName(), dish.getImage(), dish.getPrice(), quantityToAdd));
                    
                    }
                }
                
            }
            
        } else if ("update".equals(action)) {
        	
            int quantityToSet = (qty == null) ? 1 : qty;
            
            if (dishId != null && cart.containsKey(dishId)) {
            	
                if (quantityToSet <= 0) {
                	
                    cart.remove(dishId);
                    
                } else {
                	
                    cart.get(dishId).setQuantity(quantityToSet);
                    
                }
            } else {
            	
                error = "Item not in cart.";
            }
        } else if ("remove".equals(action)) {
        	
            if (dishId != null) {
            	
                cart.remove(dishId);
                
            }
        } else if ("clear".equals(action)) {
        	
        	
            cart.clear();
            
        }

        String redirect = req.getParameter("redirect");
        
        
        if (redirect == null || redirect.trim().isEmpty()) {
        	
            redirect = req.getContextPath() + "/pages/user/cart.jsp";
            
        }
        if (error != null) {
        	
        	
            redirect += (redirect.contains("?") ? "&" : "?") + "cartError=" + URLEncoder.encode(error, "UTF-8");
            
            
        }
        
        resp.sendRedirect(redirect);
        
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
