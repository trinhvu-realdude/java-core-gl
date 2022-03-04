package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.ItemDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.exception.DuplicateElementException;
import greatlearning.miniproject.model.Item;
import greatlearning.miniproject.model.Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemService implements ItemDAO {
    private Connection connection = DBConnect.getConnection();

    private static class ItemDAOHelper {
        private static final ItemService INSTANCE = new ItemService();
    }

    private ItemService() {
    }

    public static ItemService getInstance() {
        return ItemDAOHelper.INSTANCE;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();

        try {
            String sql = "SELECT * FROM items";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                items.add(new Item(id, name, price));
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }

        return items;
    }

    @Override
    public List<Item> searchItemsByName(String search) {
        List<Item> result = new ArrayList<>();

        try {
            String sql = "SELECT * FROM items WHERE name LIKE '%" + search + "%'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                result.add(new Item(id, name, price));
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }

        return result;
    }

    @Override
    public Item getItemById(int itemId) {
        Item item = null;

        try {
            String sql = "SELECT * FROM items WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                item = new Item(id, name, price);
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }

        return item;
    }

    @Override
    public HashMap<Integer, Item> getItemsByOrderId(int orderDetailId) {
        HashMap<Integer, Item> items = new HashMap<>();

        try {
            String sql = "SELECT od.id, i.id as itemId, i.name, i.price, od.numberOfPlates " +
                    "FROM orders o \n" +
                    "INNER JOIN order_details od, items i \n" +
                    "WHERE o.id = ? \n" +
                    "AND o.id = od.orderId \n" +
                    "AND i.id = od.itemId";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderDetailId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int itemId = rs.getInt("itemId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                items.put(id, new Item(itemId, name, price));
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return items;
    }

    @Override
    public void addItem(Item item) {
        try {
            if (!isDuplicated(item.getName())) {
                String sql = "INSERT INTO items (name, price) VALUES (?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, item.getName());
                ps.setDouble(2, item.getPrice());
                ps.executeUpdate();

                System.out.println("Add new item successfully!");
            } else {
                throw new DuplicateElementException();
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        } catch (DuplicateElementException e) {
            System.err.println("Item already existed! Try again.");;
        }
    }

    @Override
    public boolean updateItem(Item item) {
        boolean update = false;
        try {
            String sql = "UPDATE items SET name = ?, price = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getId());
            update = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return update;
    }

    @Override
    public boolean deleteItem(Item item) {
        boolean delete = false;
        try {
            String sql = "DELETE FROM items WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, item.getId());
            delete = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return delete;
    }

    public boolean deleteItemInMenuDetails(Item item) {
        boolean delete = false;
        try {
            String sql = "DELETE FROM menu_details WHERE itemId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, item.getId());
            delete = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return delete;
    }

    public List<String> getItemNameByMenuId(int menuId) {
        List<String> itemNames = new ArrayList<>();
        try {
            String sql = "SELECT i.name AS itemName \n" +
                    "FROM menu_details md \n" +
                    "INNER JOIN menu m, items i \n" +
                    "WHERE md.menuId = ? \n" +
                    "AND md.itemId = i.id \n" +
                    "AND md.menuId = m.id";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, menuId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("itemName");
                itemNames.add(name);
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return itemNames;
    }

    public boolean isDuplicated(String name) {
        List<Item> items = getAllItems();

        for (Item item : items) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}