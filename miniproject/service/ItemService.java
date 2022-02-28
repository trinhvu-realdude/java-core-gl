package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.ItemDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Item;

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
}
