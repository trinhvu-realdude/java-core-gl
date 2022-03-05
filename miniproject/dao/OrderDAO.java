package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.Bill;
import greatlearning.miniproject.model.Order;
import greatlearning.miniproject.model.OrderDetails;

import java.util.HashMap;

public interface OrderDAO {

    int createOrder(Order order);

    void createOrderDetails(OrderDetails orderDetails);

    HashMap<Integer, Bill> getBillByUserId(int userId);

    int getNumberOfPlatesById(int id);

    HashMap<Integer, Bill> getBillsByDate(java.sql.Date today);

    double getTotalSaleByMonth(int month);

    boolean deleteItemInOrderDetails(int itemId);
}
