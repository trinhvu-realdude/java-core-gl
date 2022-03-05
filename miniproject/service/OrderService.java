package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.OrderDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Bill;
import greatlearning.miniproject.model.Order;
import greatlearning.miniproject.model.OrderDetails;

import java.sql.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Class: OrderService implements OrderDAO interface
 *
 * Applied Bill Pugh Singleton Pattern for Order service
 */
public class OrderService implements OrderDAO {
    private Connection connection = DBConnect.getConnection();

    /**
     * Inner Static Class: OrderDAOHelper
     */
    private static class OrderDAOHelper {
        private static final OrderService INSTANCE = new OrderService();
    }

    private OrderService() {
        // private constructor
    }

    /**
     * Function: getInstance
     * @return OrderDAOHelper.INSTANCE
     */
    public static OrderService getInstance() {
        return OrderDAOHelper.INSTANCE;
    }

    /**
     * Function: createOrder
     * @param order
     * @return id of this.order after inserting it into orders table
     */
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

    /**
     * Function: createOrderDetails
     * @param orderDetails
     * Insert orderId, itemId, numberOfPlates into order_details table
     */
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

    /**
     * Function: getBillByUserId
     * @param userId
     * @return a hash map with Integer key and Bill value
     */
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

    /**
     * Function: getNumberOfPlatesById
     * @param id
     * @return number of plates in a specific order_details row
     */
    @Override
    public int getNumberOfPlatesById(int id) {
//        List<Integer> numberOfPlates = new ArrayList<>();
        int numberOfPlates = 0;
        try {
            String sql = "SELECT numberOfPlates FROM order_details WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //                numberOfPlates.add(number);
                numberOfPlates = rs.getInt("numberOfPlates");
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return numberOfPlates;
    }

    /**
     * Function: getBillsByDate
     * @param today
     * @return a hash map with Integer key and Bill value
     */
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

    /**
     * Function: getTotalSaleByMonth
     * @param month
     * @return total sale of in current month
     */
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

    /**
     * Function: deleteItemInOrderDetails
     * @param itemId
     * @return true if deleting row where row have a specific itemId successfully, and vice versa
     */
    @Override
    public boolean deleteItemInOrderDetails(int itemId) {
        boolean delete = false;
        try {
            String sql = "DELETE FROM order_details WHERE itemId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, itemId);
            delete = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return delete;
    }
}
