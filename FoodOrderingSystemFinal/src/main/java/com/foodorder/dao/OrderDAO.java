package com.foodorder.dao;

import com.foodorder.entity.Order;
import com.foodorder.entity.OrderItem;
import com.foodorder.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDAO {

    public boolean createOrder(Order order) {
        String orderSql = "INSERT INTO orders (order_no, user_id, recipient_name, recipient_phone, " +
                "address, subtotal, delivery_fee, total_amount, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
        String itemSql = "INSERT INTO order_item (order_id, dish_id, dish_name, price, quantity, subtotal) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            int orderId;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, order.getOrderNo());
                ps.setInt(2, order.getUserId());
                ps.setString(3, order.getRecipientName());
                ps.setString(4, order.getRecipientPhone());
                ps.setString(5, order.getAddress());
                ps.setBigDecimal(6, order.getSubtotal());
                ps.setBigDecimal(7, order.getDeliveryFee());
                ps.setBigDecimal(8, order.getTotalAmount());
                ps.executeUpdate();
                
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Failed to obtain generated order ID");
                    }
                    orderId = keys.getInt(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                for (OrderItem item : order.getItems()) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getDishId());
                    ps.setString(3, item.getDishName());
                    ps.setBigDecimal(4, item.getPrice());
                    ps.setInt(5, item.getQuantity());
                    ps.setBigDecimal(6, item.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
            order.setId(orderId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Order> findByUser(Integer userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY create_time DESC";
        List<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapOrderRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public Order findByOrderNo(String orderNo) {
        String sql = "SELECT * FROM orders WHERE order_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                Order order = mapOrderRow(rs);
                order.setItems(findItems(conn, order.getId()));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    public List<Order> findAll() {
        String sql = "SELECT * FROM orders ORDER BY create_time DESC";
        List<Order> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapOrderRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public boolean updateStatus(Integer orderId, int status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }
        return false;
    }

    public Order findById(Integer id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Order order = mapOrderRow(rs);
                order.setItems(findItems(conn, order.getId()));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    public int countToday() {
        String sql = "SELECT COUNT(*) FROM orders WHERE DATE(create_time) = CURDATE()";
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

    private List<OrderItem> findItems(Connection conn, int orderId) throws SQLException {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setDishId(rs.getInt("dish_id"));
                    item.setDishName(rs.getString("dish_name"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setSubtotal(rs.getBigDecimal("subtotal"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    private Order mapOrderRow(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setOrderNo(rs.getString("order_no"));
        o.setUserId(rs.getInt("user_id"));
        o.setRecipientName(rs.getString("recipient_name"));
        o.setRecipientPhone(rs.getString("recipient_phone"));
        o.setAddress(rs.getString("address"));
        o.setSubtotal(rs.getBigDecimal("subtotal"));
        o.setDeliveryFee(rs.getBigDecimal("delivery_fee"));
        o.setTotalAmount(rs.getBigDecimal("total_amount"));
        o.setStatus(rs.getInt("status"));
        o.setCreateTime(rs.getTimestamp("create_time"));
        return o;
    }
}
