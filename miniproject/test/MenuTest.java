package greatlearning.miniproject.test;

import greatlearning.miniproject.model.Menu;
import greatlearning.miniproject.service.MenuService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    MenuService test = MenuService.getInstance();

    @Test
    void createMenu() {
        Menu menu = new Menu("Dinner");
        int actual = test.createMenu(menu);
        assertEquals(3, actual);
    }

    @Test
    void getAllMenus() {
        HashMap<Integer, Menu> expected = new HashMap<>();
        expected.put(1, new Menu("Breakfast"));
        expected.put(2, new Menu("Lunch"));
        expected.put(3, new Menu("Dinner"));
        HashMap<Integer, Menu> actual = test.getAllMenus();
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
        assertEquals(expected.get(2).getName(), actual.get(2).getName());
        assertEquals(expected.get(3).getName(), actual.get(3).getName());
    }

    @Test
    void getMenuById() {
        Menu expected = new Menu("Lunch");
        Menu actual = test.getMenuById(2);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void updateMenu() {
        Menu menu = new Menu(3,"Brunch");
        boolean actual = test.updateMenu(menu);
        assertTrue(actual);
    }
}