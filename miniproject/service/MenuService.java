package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.MenuDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Menu;
import greatlearning.miniproject.model.MenuDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuService implements MenuDAO {

    private Connection connection = DBConnect.getConnection();

    /**
     * Inner Static Class: OrderDAOHelper
     */
    private static class MenuDAOHelper {
        private static final MenuService INSTANCE = new MenuService();
    }

    private MenuService() {
        // private constructor
    }

    /**
     * Function: getInstance
     * @return OrderDAOHelper.INSTANCE
     */
    public static MenuService getInstance() {
        return MenuDAOHelper.INSTANCE;
    }


    /**
     * Function: createMenu
     * @param menu
     * @return the id of this.menu after inserting it into menu table
     */
    @Override
    public int createMenu(Menu menu) {
        int menuId = -1;
        try {
            String sql = "INSERT INTO menu (name) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, menu.getName());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                menuId = rs.getInt(1);
            }

            System.out.println("Create a menu successfully!");
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return menuId;
    }

    /**
     * Function: createMenuDetails
     * @param menuDetails
     * Insert menuId, itemId into menu_details table
     */
    @Override
    public void createMenuDetails(MenuDetails menuDetails) {
        try {
            String sql = "INSERT INTO menu_details (menuId, itemId) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            List<Integer> itemIdList = menuDetails.getItemIdList();

            for (Integer integer : itemIdList) {
                ps.setInt(1, menuDetails.getMenuId());
                ps.setInt(2, integer);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
    }

    /**
     * Function: getAllMenus
     * @return a hash map with Integer key and Menu value
     */
    @Override
    public HashMap<Integer, Menu> getAllMenus() {
        HashMap<Integer, Menu> menus = new HashMap<>();
        try {
            String sql = "SELECT * FROM menu";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                menus.put(id, new Menu(name));
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return menus;
    }

    /**
     * Function: getMenuById
     * @param id
     * @return a menu with a specific id
     */
    @Override
    public Menu getMenuById(int id) {
        Menu menu = null;
        try {
            String sql = "SELECT name FROM menu WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                menu = new Menu(id, name);
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return menu;
    }

    /**
     * Function: updateMenu
     * @param newMenu
     * @return update information of a menu in table
     */
    @Override
    public boolean updateMenu(Menu newMenu) {
        boolean update = false;
        try {
            String sql = "UPDATE menu SET name = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newMenu.getName());
            ps.setInt(2, newMenu.getId());
            update = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return update;
    }

    @Override
    public boolean updateMenuDetails(int id, int itemId) {
        boolean update = false;
        try {
                String sql = "UPDATE menu_details SET itemId = ? WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, itemId);
                ps.setInt(2, id);
                update = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return update;
    }

    /**
     * Function: getMenuDetailsByMenuId
     * @param menuId
     * @return a list of menu detail id
     */
    @Override
    public List<Integer> getMenuDetailsByMenuId(int menuId) {
        List<Integer> menuDetails = new ArrayList<>();
        try {
            String sql = "SELECT * FROM menu_details WHERE menuId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, menuId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                menuDetails.add(id);
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return menuDetails;
    }

    /**
     * Function: deleteMenu
     * @param id
     * @return true if delete a menu successfully and vice versa
     */
    @Override
    public boolean deleteMenu(int id) {
        boolean delete = false;
        try {
            String sql = "DELETE FROM menu WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            delete = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return delete;
    }

    /**
     * Function: deleteMenuDetails
     * @param id
     * @return true if delete a row in menu_details table existing input menuId
     */
    @Override
    public boolean deleteMenuDetails(int id) {
        boolean delete = false;
        try {
            String sql = "DELETE FROM menu_details WHERE menuId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            delete = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return delete;
    }
}
