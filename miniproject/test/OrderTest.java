package greatlearning.miniproject.test;

import greatlearning.miniproject.model.Bill;
import greatlearning.miniproject.model.Order;
import greatlearning.miniproject.service.OrderService;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    OrderService test = OrderService.getInstance();

    @Test
    void createOrder() {
        Order order = new Order(3, 45.89, 2);
        int actual = test.createOrder(order);
        assertEquals(4, actual);
    }

    @Test
    void getBillByUserId() {
        HashMap<Integer, Bill> bills = new HashMap<>();
        bills.put(1, new Bill(2, 12.10, Date.valueOf("2022-03-01"), "Processing"));
        HashMap<Integer, Bill> actual = test.getBillByUserId(2);
        assertEquals(bills.get(1).getDate(), actual.get(1).getDate());
        assertEquals(bills.get(1).getQuantity(), actual.get(1).getQuantity());
        assertEquals(bills.get(1).getStatus(), actual.get(1).getStatus());
        assertEquals(bills.get(1).getTotal(), actual.get(1).getTotal());
    }

    @Test
    void getNumberOfPlatesById() {
        int numberOfPlates = 10;
        int actual = test.getNumberOfPlatesById(3);
        assertEquals(numberOfPlates, actual);

        numberOfPlates = 9;
        actual = test.getNumberOfPlatesById(5);
        assertEquals(numberOfPlates, actual);
    }

    @Test
    void getBillsByDate() {
        HashMap<Integer, Bill> bills = new HashMap<>();
        bills.put(2, new Bill(2, 46.34, Date.valueOf("2022-03-02"), "Processing"));
        bills.put(3, new Bill(1, 17.01, Date.valueOf("2022-03-02"), "Processing"));

        HashMap<Integer, Bill> actual = test.getBillsByDate(Date.valueOf("2022-03-02"));
        assertEquals(bills.get(2).getTotal(), actual.get(2).getTotal());
        assertEquals(bills.get(2).getQuantity(), actual.get(2).getQuantity());

        assertEquals(bills.get(3).getTotal(), actual.get(3).getTotal());
        assertEquals(bills.get(3).getQuantity(), actual.get(3).getQuantity());
    }

    @Test
    void getTotalSaleByMonth() {
        double total = 75.45;
        double actual = test.getTotalSaleByMonth(3);
        assertEquals(total, actual);
    }

    @Test
    void deleteItemInOrderDetails() {
        boolean actual = test.deleteItemInOrderDetails(2);
        assertTrue(actual);
    }
}