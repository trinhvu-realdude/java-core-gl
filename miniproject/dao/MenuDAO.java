package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.Menu;
import greatlearning.miniproject.model.MenuDetails;

import java.util.HashMap;
import java.util.List;

public interface MenuDAO {

    int createMenu(Menu menu);

    void createMenuDetails(MenuDetails menuDetails);

    HashMap<Integer, Menu> getAllMenus();

    Menu getMenuById(int id);

    boolean updateMenu(Menu newMenu);

    boolean updateMenuDetails(int id, int itemId);

    List<Integer> getMenuDetailsByMenuId(int menuId);

    boolean deleteMenu(int id);

    boolean deleteMenuDetails(int id);
}
