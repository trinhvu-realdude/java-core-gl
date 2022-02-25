package greatlearning.miniproject.dao;

import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillDAOImpl implements BillDAO {
    private Connection connection = DBConnect.getConnection();

    private static class BillDAOHelper {
        private static final BillDAOImpl INSTANCE = new BillDAOImpl();
    }

    private BillDAOImpl() {
    }

    public static BillDAOImpl getInstance() {
        return BillDAOImpl.BillDAOHelper.INSTANCE;
    }

    @Override
    public void createBill(Bill bill) {
        try {
            String sql = "INSERT INTO bills (orderId, itemId) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, bill.getOrderId());
            ps.setInt(2, bill.getItemId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Data error!");
        }
    }
}
