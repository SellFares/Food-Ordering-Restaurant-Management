package com.foodorder.servlet;

import com.foodorder.dao.DishDAO;
import com.foodorder.dao.OrderDAO;
import com.foodorder.entity.*;
import com.foodorder.util.OrderNoUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {

    private static final BigDecimal DELIVERY_FEE = new BigDecimal("3.00");

    private final DishDAO dishDAO = new DishDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/pages/user/login.jsp?error=notLogin");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "submit":
                handleSubmit(req, resp, session, loginUser);
                break;
            case "detail":
                handleDetail(req, resp);
                break;
            case "list":
            default:
                handleList(req, resp, loginUser);
                break;
        }
    }

    private void handleSubmit(HttpServletRequest req, HttpServletResponse resp,
                               HttpSession session, User loginUser)
            throws ServletException, IOException {

        String recipientName = req.getParameter("recipientName");
        
        String recipientPhone = req.getParameter("recipientPhone");
        
        String address = req.getParameter("address");
        

        String errorMsg = null;

        if (recipientName == null || recipientName.trim().isEmpty()) {
        	
            errorMsg = "Recipient name is required."; 
            
        } else if (recipientPhone == null || !recipientPhone.matches("^[0-9+\\-\\s]{7,20}$")) {
        	
            errorMsg = "Please enter a valid contact number.";
            
        } else if (address == null || address.trim().isEmpty()) {
        	
            errorMsg = "Delivery address is required.";
            
        }

        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (errorMsg == null && (cart == null || cart.isEmpty())) {
        	
            errorMsg = "Your cart is empty.";
            
        }

        List<OrderItem> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        if (errorMsg == null) {
        	
            for (CartItem ci : cart.values()) {
            	
                Dish dish = dishDAO.findById(ci.getDishId());
                
                if (dish == null) {
                	
                    errorMsg = "'" + ci.getDishName() + "' is no longer available.";
                    break;
                    
                }
                if (dish.getStock() != null && dish.getStock() < ci.getQuantity()) {
                	
                    errorMsg = "Not enough stock for '" + dish.getName() + "'. Available: " + dish.getStock();
                    break;
                    
                }
                OrderItem item = new OrderItem(dish.getId(), dish.getName(), dish.getPrice(), ci.getQuantity());
                items.add(item);
                subtotal = subtotal.add(item.getSubtotal());
            }
        }

        if (errorMsg == null) {
        	
            for (OrderItem item : items) {
            	
                boolean reduced = dishDAO.reduceStock(item.getDishId(), item.getQuantity());
                
                if (!reduced) {
                	
                    errorMsg = "'" + item.getDishName() + "' just went out of stock.";
                    break;
                    
                }
            }
        }

        if (errorMsg != null) {
        	
            req.setAttribute("checkoutError", errorMsg);
            req.getRequestDispatcher("/pages/user/checkout.jsp").forward(req, resp);
            return;
            
        }

        Order order = new Order();
	        order.setOrderNo(OrderNoUtil.generate());
	        order.setUserId(loginUser.getId());
	        order.setRecipientName(recipientName.trim());
	       order.setRecipientPhone(recipientPhone.trim());
	        order.setAddress(address.trim());
	       order.setSubtotal(subtotal);
	       order.setDeliveryFee(DELIVERY_FEE);
	        order.setTotalAmount(subtotal.add(DELIVERY_FEE));
	       order.setItems(items);

        boolean saved = orderDAO.createOrder(order);
        if (!saved) {
        	
            req.setAttribute("checkoutError", "Could not save your order, please try again.");
            
            req.getRequestDispatcher("/pages/user/checkout.jsp").forward(req, resp);
            
            return;
        }

        cart.clear();
        
        
        req.setAttribute("order", order);
        
        req.getRequestDispatcher("/pages/user/orderConfirm.jsp").forward(req, resp);
    }

    private void handleDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        String orderNo = req.getParameter("orderNo");
        
        Order order = (orderNo == null) ? null : orderDAO.findByOrderNo(orderNo);
        
        if (order == null) {
        
            resp.sendRedirect(req.getContextPath() + "/orderServlet?action=list");
            return;
            
        }
        
        req.setAttribute("order", order);
        
        req.getRequestDispatcher("/pages/user/orderDetail.jsp").forward(req, resp);
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp, User loginUser)
            throws ServletException, IOException {
    	
        List<Order> orders = orderDAO.findByUser(loginUser.getId());
        
        req.setAttribute("orders", orders);
        
        req.getRequestDispatcher("/pages/user/orderList.jsp").forward(req, resp);
    }
}
