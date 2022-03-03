package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.Bill;
import greatlearning.miniproject.model.Item;
import greatlearning.miniproject.model.Order;
import greatlearning.miniproject.model.OrderDetails;

import java.util.HashMap;
import java.util.List;

public interface OrderDAO {

    int createOrder(Order order);

    void createOrderDetails(OrderDetails orderDetails);

    HashMap<Integer, Bill> getBillByUserId(int userId);

    List<Integer> getNumberOfPlatesById(int id);

    HashMap<Integer, Bill> getBillsByDate(java.sql.Date today);

    double getTotalSaleByMonth(int month);
}
