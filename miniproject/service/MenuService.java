package greatlearning.miniproject.service;

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

public class MenuService {

    private Connection connection = DBConnect.getConnection();

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

    public boolean updateMenuDetails(MenuDetails menuDetails) {
        boolean update = false;
        try {
//            for (Integer itemId : menuDetails.getItemIdList()) {
//                String sql = "UPDATE menu_details SET itemId = ? WHERE menuId = ?";
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setInt(1, itemId);
//                ps.setInt(2, menuDetails.getMenuId());
//                update = ps.executeUpdate() > 0;
//            }
            int i = 0;
            while (i < menuDetails.getItemIdList().size()) {
                String sql = "UPDATE menu_details SET itemId = ? WHERE menuId = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, menuDetails.getItemIdList().get(i));
                ps.setInt(2, menuDetails.getMenuId());
                update = ps.executeUpdate() > 0;
                i++;
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return update;
    }
}
