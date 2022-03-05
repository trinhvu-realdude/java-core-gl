package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.ItemDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.exception.DuplicateElementException;
import greatlearning.miniproject.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class: ItemService implements ItemDAO interface
 *
 * Applied Bill Pugh Singleton Pattern for Item service
 */
public class ItemService implements ItemDAO {
    private Connection connection = DBConnect.getConnection();

    /**
     * Inner Static Class: ItemDAOHelper
     *
     * Create a new instance
     */
    private static class ItemDAOHelper {
        private static final ItemService INSTANCE = new ItemService();
    }

    private ItemService() {
        // private constructor
    }

    /**
     * Function: getInstance
     * @return ItemDAOHelper.INSTANCE
     */
    public static ItemService getInstance() {
        return ItemDAOHelper.INSTANCE;
    }

    /**
     * Function: getAllItems
     * @return a list of item existed in items table
     */
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

    /**
     * Function: searchItemByName
     * @param search
     * @return a list of item that match with search from user
     */
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

    /**
     * Function: getItemById
     * @param itemId
     * @return an item that having id equals to input itemId
     */
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

    /**
     * Function: getItemsByOrderId
     * @param orderId
     * @return a hash map of items with specific orderIds
     */
    @Override
    public HashMap<Integer, Item> getItemsByOrderId(int orderId) {
        HashMap<Integer, Item> items = new HashMap<>();

        try {
            String sql = "SELECT od.id, i.id as itemId, i.name, i.price, od.numberOfPlates " +
                    "FROM orders o \n" +
                    "INNER JOIN order_details od, items i \n" +
                    "WHERE o.id = ? \n" +
                    "AND o.id = od.orderId \n" +
                    "AND i.id = od.itemId";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
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

    /**
     * Function: addItem
     * @param item
     * Insert a new item into items table
     */
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

    /**
     * Function: updateItem
     * @param item
     * @return true if update an item in table successfully and vice versa
     */
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

    /**
     * Function: deleteItem
     * @param item
     * @return true if delete an item in table successfully and vice versa
     */
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

    /**
     * Function: deleteItemInMenuDetails
     * @param item
     * @return true if delete row in menu_details table having input itemId and vice versa
     */
    @Override
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

    /**
     * Function: getItemNameByMenuId
     * @param menuId
     * @return a list of item name existing in menu table
     */
    @Override
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

    /**
     * Function: isDuplicated
     * @param name
     * @return true if item name already existed in items table, and vice versa
     */
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
