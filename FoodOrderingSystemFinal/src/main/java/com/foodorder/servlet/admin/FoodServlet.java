package com.foodorder.servlet.admin;

import com.foodorder.dao.CategoryDAO;
import com.foodorder.dao.DishDAO;
import com.foodorder.entity.Dish;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/foodServlet")
public class FoodServlet extends HttpServlet {

    private final DishDAO dishDAO = new DishDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            Integer id = parseInt(req.getParameter("id"));
            if (id != null) dishDAO.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/foodServlet");
            return;
        }
        showList(req, resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        Dish dish = new Dish();
        dish.setId(parseInt(req.getParameter("id")));
        dish.setCategoryId(parseInt(req.getParameter("categoryId")));
        dish.setName(req.getParameter("name"));
        dish.setImage(req.getParameter("image"));
        dish.setIngredients(req.getParameter("ingredients"));
        dish.setNutrition(req.getParameter("nutrition"));
        dish.setDescription(req.getParameter("description"));

        String error = validate(dish, req.getParameter("price"), req.getParameter("rating"), req.getParameter("stock"));
        if (error == null) {
            try {
                dish.setPrice(new BigDecimal(req.getParameter("price")));
                dish.setRating(Double.parseDouble(req.getParameter("rating")));
                dish.setStock(Integer.parseInt(req.getParameter("stock")));
            } catch (NumberFormatException e) {
                error = "Price, rating and stock must be valid numbers.";
            }
        }

        if (error != null) {
            showList(req, resp, error);
            return;
        }

        boolean ok;
        if ("update".equals(action) && dish.getId() != null) {
            ok = dishDAO.update(dish);
        } else {
            ok = dishDAO.insert(dish);
        }

        if (!ok) {
            showList(req, resp, "Could not save the dish, please try again.");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/admin/foodServlet");
    }

    private String validate(Dish dish, String price, String rating, String stock) {
        if (dish.getCategoryId() == null) return "Please choose a category.";
        if (dish.getName() == null || dish.getName().trim().isEmpty()) return "Dish name is required.";
        if (price == null || price.trim().isEmpty()) return "Price is required.";
        if (rating == null || rating.trim().isEmpty()) return "Rating is required.";
        if (stock == null || stock.trim().isEmpty()) return "Stock is required.";
        return null;
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp, String error)
            throws ServletException, IOException {
        req.setAttribute("dishes", dishDAO.findAll());
        req.setAttribute("categories", categoryDAO.findAll());
        if (error != null) req.setAttribute("error", error);
        req.getRequestDispatcher("/pages/admin/foodManage.jsp").forward(req, resp);
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
