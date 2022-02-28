package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.OrderDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Order;

import java.sql.*;
import java.sql.SQLException;

public class OrderService implements OrderDAO {
    private Connection connection = DBConnect.getConnection();

    private static class OrderDAOHelper {
        private static final OrderService INSTANCE = new OrderService();
    }

    private OrderService() {
    }

    public static OrderService getInstance() {
        return OrderDAOHelper.INSTANCE;
    }

    @Override
    public int createOrder(Order order) {
        int orderId = -1;
        try {
            String sql = "INSERT INTO orders (quantity, totalPrice, status, orderDate, userId) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});

            ps.setInt(1, order.getQuantity());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.setDate(4, new java.sql.Date(order.getOrderDate().getTime()));
            ps.setInt(5, order.getUserId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            System.out.println("Your order successfully has been placed!");
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return orderId;
    }
}
