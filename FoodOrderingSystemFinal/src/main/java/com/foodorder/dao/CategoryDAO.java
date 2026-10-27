package com.foodorder.dao;

import com.foodorder.entity.Category;
import com.foodorder.util.DBUtil;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;




public class CategoryDAO {

    public List<Category> findAll() {
        String sql = "SELECT id, name FROM category ORDER BY id";
        
        
        List<Category> list = new ArrayList<>();
        Connection conn = null;
          PreparedStatement ps = null;
          	ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public Category findById(Integer id) {
        String sql = "SELECT id, name FROM category WHERE id = ?";
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            
            	rs = ps.executeQuery();
            	
            	if (rs.next()) {
            		return new Category(rs.getInt("id"), rs.getString("name"));
            	}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    public boolean insert(Category category) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        
        try {
            conn = DBUtil.getConnection();
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, category.getName());
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    public boolean update(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    public boolean delete(Integer id) {
        if (countDishes(id) > 0) {
            return false; 
        }
        
        String sql = "DELETE FROM category WHERE id = ?";
        
        
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

    public int countDishes(Integer id) {
    	
    	
        String sql = "SELECT COUNT(*) FROM dish WHERE category_id = ?";
        
        
        Connection conn = null;
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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
}
