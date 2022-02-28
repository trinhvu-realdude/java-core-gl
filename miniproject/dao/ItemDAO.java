package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.Item;

import java.util.HashMap;
import java.util.List;

public interface ItemDAO {

    List<Item> getAllItems();

    List<Item> searchItemsByName(String search);

    Item getItemById(int itemId);

    HashMap<Integer, Item> getItemsByOrderId(int orderId);
}
