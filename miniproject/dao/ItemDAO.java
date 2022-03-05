package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.Item;

import java.util.HashMap;
import java.util.List;

public interface ItemDAO {

    List<Item> getAllItems();

    List<Item> searchItemsByName(String search);

    Item getItemById(int itemId);

    HashMap<Integer, Item> getItemsByOrderId(int orderId);

    void addItem(Item item);

    boolean updateItem(Item item);

    boolean deleteItem(Item item);

    boolean deleteItemInMenuDetails(Item item);

    List<String> getItemNameByMenuId(int menuId);
}
