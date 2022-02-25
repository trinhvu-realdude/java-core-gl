package greatlearning.miniproject.dao;

import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    private Connection connection = DBConnect.getConnection();

    private static class ItemDAOHelper {
        private static final ItemDAOImpl INSTANCE = new ItemDAOImpl();
    }

    private ItemDAOImpl() {
    }

    public static ItemDAOImpl getInstance() {
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
}