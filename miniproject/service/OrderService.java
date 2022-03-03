package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.OrderDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Bill;
import greatlearning.miniproject.model.Order;
import greatlearning.miniproject.model.OrderDetails;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Override
    public void createOrderDetails(OrderDetails orderDetails) {
        try {
            String sql = "INSERT INTO order_details (orderId, itemId, numberOfPlates) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Integer> itemIdList = orderDetails.getItemIdList();
            List<Integer> numberOfPlatesList = orderDetails.getNumberOfPlatesList();

            for (int i = 0; i < itemIdList.size(); i++) {
                ps.setInt(1, orderDetails.getOrderId());
                ps.setInt(2, itemIdList.get(i));
                ps.setInt(3, numberOfPlatesList.get(i));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
    }

    @Override
    public HashMap<Integer, Bill> getBillByUserId(int userId) {
        HashMap<Integer, Bill> bills = new HashMap<>();

        try {
            String sql = "SELECT o.id AS orderId, " +
                    "o.status AS status, " +
                    "o.quantity AS quantity, " +
                    "o.totalPrice AS total, " +
                    "o.orderDate AS date \n" +
                    "FROM orders o \n" +
                    "INNER JOIN users u, order_details od, items i \n" +
                    "WHERE o.status = 'Processing' \n" +
                    "AND o.userId = ? \n" +
                    "AND u.id = o.userId \n" +
                    "AND o.id = od.orderId \n" +
                    "AND i.id = od.itemId";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("orderId");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                java.util.Date date = rs.getDate("date");
                String status = rs.getString("status");
                bills.put(id, new Bill(quantity, total, date, status));
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return bills;
    }

    @Override
    public List<Integer> getNumberOfPlatesById(int id) {
        List<Integer> numberOfPlates = new ArrayList<>();
        try {
            String sql = "SELECT numberOfPlates FROM order_details WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int number = rs.getInt("numberOfPlates");
                numberOfPlates.add(number);
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return numberOfPlates;
    }

    @Override
    public HashMap<Integer, Bill> getBillsByDate(java.sql.Date today) {
        HashMap<Integer, Bill> bills = new HashMap<>();

        try {
            String sql = "SELECT o.id AS orderId, \n" +
                    "o.quantity, o.status, \n" +
                    "i.name AS item, " +
                    "o.totalPrice AS total, " +
                    "o.orderDate AS date\n" +
                    "FROM orders o \n" +
                    "INNER JOIN users u, order_details od, items i\n" +
                    "WHERE o.orderDate = ? \n" +
                    "AND u.id = o.userId \n" +
                    "AND o.id = od.orderId\n" +
                    "AND i.id = od.itemId";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, today);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("orderId");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                String status = rs.getString("status");
                java.util.Date date = rs.getDate("date");
                bills.put(id, new Bill(quantity, total, date, status));
            }

        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return bills;
    }

    @Override
    public double getTotalSaleByMonth(int month) {
        double totalSale = 0;
        try {
            String sql = "SELECT sum(o.totalPrice) AS totalSale FROM orders o WHERE month(o.orderDate) = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                totalSale = rs.getDouble("totalSale");
            }
        } catch (SQLException e) {
            System.err.println("Data error");
        }
        return totalSale;
    }
}
