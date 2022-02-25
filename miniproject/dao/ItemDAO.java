package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.Item;

import java.util.List;

public interface ItemDAO {

    List<Item> getAllItems();

    List<Item> searchItemsByName(String search);

    Item getItemById(int itemId);
}
