package greatlearning.miniproject.test;

import greatlearning.miniproject.model.Item;
import greatlearning.miniproject.service.ItemService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    ItemService test = ItemService.getInstance();

    @Test
    void searchItemsByName() {
        List<Item> expected = new ArrayList<>();
        expected.add(new Item(2, "French Fries", 1.89));
        expected.add(new Item(5, "Fried Rice", 3.28));
        List<Item> actual = test.searchItemsByName("F");

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
            assertEquals(expected.get(i).getPrice(), actual.get(i).getPrice());
        }
    }

    @Test
    void getItemById() {
        Item expected = new Item(2, "French Fries", 1.89);
        Item actual = test.getItemById(2);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    void getItemsByOrderId() {
        HashMap<Integer, Item> expected = new HashMap<>();
        expected.put(3, new Item(3, "Ice Cream", 1.45));
        expected.put(4, new Item(9, "Pumpkin Pie", 3.98));
        HashMap<Integer, Item> actual = test.getItemsByOrderId(2);

        assertEquals(expected.get(3).getId(), actual.get(3).getId());
        assertEquals(expected.get(3).getName(), actual.get(3).getName());
        assertEquals(expected.get(3).getPrice(), actual.get(3).getPrice());

        assertEquals(expected.get(4).getId(), actual.get(4).getId());
        assertEquals(expected.get(4).getName(), actual.get(4).getName());
        assertEquals(expected.get(4).getPrice(), actual.get(4).getPrice());
    }

    @Test
    void updateItem() {
        Item item = new Item(15, "Chicken Soup", 5.99);
        boolean actual = test.updateItem(item);
        assertTrue(actual);
    }

    @Test
    void getItemNameByMenuId() {
        List<String> expected = new ArrayList<>();
        expected.add("Pumpkin Pie");
        expected.add("Chicken Pot Pie");
        List<String> actual = test.getItemNameByMenuId(2);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}