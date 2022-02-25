package greatlearning.miniproject.dao;

import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Order;

import java.sql.*;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    private Connection connection = DBConnect.getConnection();

    private static class OrderDAOHelper {
        private static final OrderDAOImpl INSTANCE = new OrderDAOImpl();
    }

    private OrderDAOImpl() {
    }

    public static OrderDAOImpl getInstance() {
        return OrderDAOHelper.INSTANCE;
    }

    @Override
    public void createOrder(Order order) {
        try {
            String sql = "INSERT INTO orders (quantity, totalPrice, status, orderDate, userId) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, order.getQuantity());
            ps.setDouble(2, order.getTotalPrice());
            ps.setString(3, order.getStatus());
            ps.setDate(4, (Date) order.getOrderDate());
            ps.setInt(5, order.getUserId());

            ps.executeUpdate();

            System.out.println("Your order successfully has been placed!");
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
    }
}
