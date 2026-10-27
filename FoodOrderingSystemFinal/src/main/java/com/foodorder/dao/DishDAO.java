package com.foodorder.dao;

import com.foodorder.entity.Dish;
import com.foodorder.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DishDAO {

    public List<Dish> findAll() {
        String sql = "SELECT d.*, c.name AS category_name FROM dish d " + "LEFT JOIN category c ON d.category_id = c.id ORDER BY d.id";
        return query(sql);
    }

    public List<Dish> findByCategory(Integer categoryId) {
    	
    	
        String sql = "SELECT d.*, c.name AS category_name FROM dish d " + "LEFT JOIN category c ON d.category_id = c.id WHERE d.category_id = ? ORDER BY d.id";
        
        List<Dish> list = new ArrayList<>();
        
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, categoryId);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public Dish findById(Integer id) {
    	
    	
        String sql = "SELECT d.*, c.name AS category_name FROM dish d " + "LEFT JOIN category c ON d.category_id = c.id WHERE d.id = ?";
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        
        ResultSet rs = null;
        
        try {
        	
            conn = DBUtil.getConnection();
            
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            
            rs = ps.executeQuery();
            
            
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    public boolean reduceStock(Integer dishId, int qty) {
        
    	
    	String sql = "UPDATE dish SET stock = stock - ? WHERE id = ? AND stock >= ?";
        
    	Connection conn = null;
        
        PreparedStatement ps = null;
     
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, qty);
            
            ps.setInt(2, dishId);
            
            ps.setInt(3, qty);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    public boolean insert(Dish dish) {
    	
    	
        String sql = "INSERT INTO dish (category_id, name, price, image, ingredients, nutrition, description, rating, stock) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        
        try {
            conn = DBUtil.getConnection();
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, dish.getCategoryId());
	            ps.setString(2, dish.getName());
	            ps.setBigDecimal(3, dish.getPrice());
	            ps.setString(4, dish.getImage());
	            ps.setString(5, dish.getIngredients());
	            ps.setString(6, dish.getNutrition());
	            ps.setString(7, dish.getDescription());
	            ps.setDouble(8, dish.getRating() == null ? 0 : dish.getRating());
	            ps.setInt(9, dish.getStock() == null ? 0 : dish.getStock());
	            return ps.executeUpdate() > 0;
	            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    public boolean update(Dish dish) {
      
    	
    	String sql = "UPDATE dish SET category_id=?, name=?, price=?, image=?, ingredients=?, " + "nutrition=?, description=?, rating=?, stock=? WHERE id=?";
        
    	Connection conn = null;
        
    	PreparedStatement ps = null;
        
    	
    	try {
            conn = DBUtil.getConnection();
            
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, dish.getCategoryId());
            
            ps.setString(2, dish.getName());
            
            ps.setBigDecimal(3, dish.getPrice());
            
            ps.setString(4, dish.getImage());
            
            ps.setString(5, dish.getIngredients());
            
            ps.setString(6, dish.getNutrition());
            
            ps.setString(7, dish.getDescription());
            
            ps.setDouble(8, dish.getRating() == null ? 0 : dish.getRating());
            
            ps.setInt(9, dish.getStock() == null ? 0 : dish.getStock());
            
            ps.setInt(10, dish.getId());
            return ps.executeUpdate() > 0;
        
    	} catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    public boolean delete(Integer id) {
    	
        String sql = "DELETE FROM dish WHERE id = ?";
        
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        try {
        	
            conn = DBUtil.getConnection();
            
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    public int countAll() {
    	
    	
        String sql = "SELECT COUNT(*) FROM dish";
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        
        try {
        	
            conn = DBUtil.getConnection();
            
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return 0;
    }

    private List<Dish> query(String sql) {
    	
    	
        List<Dish> list = new ArrayList<>();
        
       
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
        	
	            
        	conn = DBUtil.getConnection();
	            
        	ps = conn.prepareStatement(sql);
	            
        	rs = ps.executeQuery();
        	
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    private Dish mapRow(ResultSet rs) throws SQLException {
        Dish d = new Dish();
	        d.setId(rs.getInt("id"));
	        d.setCategoryId(rs.getInt("category_id"));
	        d.setCategoryName(rs.getString("category_name"));
	        d.setName(rs.getString("name"));
	        d.setPrice(rs.getBigDecimal("price"));
	        d.setImage(rs.getString("image"));
	        d.setIngredients(rs.getString("ingredients"));
	        d.setNutrition(rs.getString("nutrition"));
	        d.setDescription(rs.getString("description"));
	        d.setRating(rs.getDouble("rating"));
	        d.setStock(rs.getInt("stock"));
        return d;
    }
}
