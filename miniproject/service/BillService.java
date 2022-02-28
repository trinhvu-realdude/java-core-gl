package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.BillDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BillService implements BillDAO {
    private Connection connection = DBConnect.getConnection();

    private static class BillDAOHelper {
        private static final BillService INSTANCE = new BillService();
    }

    private BillService() {
    }

    public static BillService getInstance() {
        return BillService.BillDAOHelper.INSTANCE;
    }

    @Override
    public void createBill(Bill bill) {
        try {
            String sql = "INSERT INTO bills (orderId, itemId) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Integer> itemIdList = bill.getItemIdList();

            for (Integer itemId : itemIdList) {
                ps.setInt(1, bill.getOrderId());
                ps.setInt(2, itemId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
    }
}
