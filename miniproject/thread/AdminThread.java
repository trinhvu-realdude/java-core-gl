package greatlearning.miniproject.thread;

import greatlearning.miniproject.exception.NegativeInputException;
import greatlearning.miniproject.model.Bill;
import greatlearning.miniproject.model.Item;
import greatlearning.miniproject.model.Menu;
import greatlearning.miniproject.model.MenuDetails;
import greatlearning.miniproject.service.ItemService;
import greatlearning.miniproject.service.MenuService;
import greatlearning.miniproject.service.OrderService;
import greatlearning.miniproject.service.UserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class AdminThread {
    Scanner sc = new Scanner(System.in);

    public Runnable admin = () -> {
        boolean isStop = false;

        java.sql.Date today = new java.sql.Date(new Date().getTime());

        ItemService itemService = ItemService.getInstance();
        OrderService orderService = OrderService.getInstance();
        UserService userService = UserService.getInstance();

        while (!isStop) {
            System.out.println("-- ADMIN OPTION --");
            System.out.println("1. Press 1 to display all the bills generated today (" + today + ")");
            System.out.println("2. Press 2 to display the total sale from this month");
            System.out.println("3. Press 3 to open the items management menu");
            System.out.println("4. Press 4 to open the bills management menu");
            System.out.println("0. Press 0 to logout");

            String option = sc.nextLine();

            switch (option) {

                // Display all bills today
                case "1" -> {
                    System.out.println("-- DISPLAY ALL BILLS TODAY --");

                    HashMap<Integer, Bill> bills = orderService.getBillsByDate(today);

                    for (Integer id : bills.keySet()) {
                        System.out.println("[" + bills.get(id).getDate() + "] Bill ID: " + id + " (quantity: " + bills.get(id).getQuantity() + ")");
                        for (Integer orderDetailId : itemService.getItemsByOrderId(id).keySet()) {
                            for (Integer numberOfPlates : orderService.getNumberOfPlatesById(orderDetailId)) {
                                System.out.println("- " + itemService.getItemsByOrderId(id).get(orderDetailId).getName()
                                        + " ($" + itemService.getItemsByOrderId(id).get(orderDetailId).getPrice() + "): "
                                        + numberOfPlates + " plates");
                            }
                        }
                        System.out.println("-> Total: $" + bills.get(id).getTotal());
                        System.out.println("-> Status: " + bills.get(id).getStatus());
                        for (Integer i : userService.getUserNameByOrderId(id, today).keySet()) {
                            System.out.println("-> Customer: " + userService.getUserNameByOrderId(id, today).get(i));
                        }
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                //
                case "2" -> {
                    System.out.println("-- TOTAL SALE --");

                    Date date = new Date();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDate.getMonthValue();

                    double totalSale = orderService.getTotalSaleByMonth(month);

                    System.out.println("Total sale of this month: $" + totalSale);

                    System.out.println("-- *-*-*-*-* --");
                }

                case "3" -> {
                    menuItem();
                }

                case "4" -> {
                    menu();
                }

                case "0" -> {
                    isStop = true;
                    System.out.println("Logout successfully!");
                }

                default -> System.out.println("Invalid input!");
            }
        }
    };

    public void menuItem() {
        ItemService itemService = ItemService.getInstance();

        boolean isStop = false;

        while (!isStop) {
            System.out.println("-- MENU ITEM --");
            System.out.println("1. Press 1 to search the item");
            System.out.println("2. Press 2 to add new item");
            System.out.println("3. Press 3 to update an item");
            System.out.println("4. Press 4 to delete an item");
            System.out.println("0. Press 0 to return admin page");

            String option = sc.nextLine();

            switch (option) {

                case "1" -> {
                    System.out.println("-- SEARCH ITEM --");

                    System.out.println("Please enter the item name you want to search:");
                    String search = sc.nextLine().trim();

                    List<Item> result = itemService.searchItemsByName(search);

                    if (result.size() == 0 || search.equals("")) {
                        System.out.println("No search results found!");
                    } else {
                        for (Item item : result) {
                            System.out.println(item.getId() + ".\t" + item.getName() + "\t($" + item.getPrice() + ")");
                        }
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "2" -> {
                    System.out.println("-- ADD ITEM --");

                    try {
                        System.out.println("Enter the new item name:");
                        String name = sc.nextLine().trim();

                        System.out.println("Enter the new price ($0.00):");
                        double price = Double.parseDouble(sc.nextLine().trim());

                        if (price <= 0) {
                            throw new NegativeInputException();
                        }

                        Item item = new Item(name, price);

                        itemService.addItem(item);
                    } catch (NegativeInputException | NumberFormatException e) {
                        System.err.println("Please enter the right format of input!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "3" -> {
                    System.out.println("-- UPDATE ITEM --");

                    List<Item> items = itemService.getAllItems();

                    try {
                        System.out.println("Enter the item id you want to update:");
                        int itemId = Integer.parseInt(sc.nextLine().trim());

                        if (itemId <= 0) {
                            throw new NegativeInputException();
                        }

                        if (itemId <= items.size()) {

                            Item item = itemService.getItemById(itemId);

                            System.out.println("Enter the new item name (" + item.getName() + "):");
                            String name = sc.nextLine().trim();

                            System.out.println("Enter the new item price ($" + item.getPrice() + "):");
                            double price = Double.parseDouble(sc.nextLine().trim());

                            item.setName(name);
                            item.setPrice(price);

                            boolean updateItem = itemService.updateItem(item);

                            if (updateItem) {
                                System.out.println("Update item successfully!");
                            } else {
                                System.out.println("Failed to update! Please try again.");
                            }
                        } else {
                            System.out.println("No item found! Pleas try again.");
                        }
                    } catch (NegativeInputException | NumberFormatException e) {
                        System.err.println("Please enter the right format of input!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "4" -> {
                    System.out.println("-- DELETE ITEM --");

                    List<Item> items = itemService.getAllItems();

                    try {
                        System.out.println("Enter the item id you want to delete:");
                        int itemId = Integer.parseInt(sc.nextLine().trim());

                        if (itemId <= 0) {
                            throw new NegativeInputException();
                        }

                        if (itemId <= items.size()) {

                            Item item = itemService.getItemById(itemId);

                            System.out.println("Item ID: " + item.getId());
                            System.out.println("- Name: " + item.getName());
                            System.out.println("- Price: $" + item.getPrice());

                            System.out.println("Are you sure to delete this item? (y/n)");
                            String sure = sc.nextLine();

                            if (sure.equals("y")) {
                                boolean deleteItem = itemService.deleteItem(item);

                                if (deleteItem) {
                                    System.out.println("Delete item successfully!");
                                } else {
                                    System.out.println("Failed to delete! Please try again.");
                                }
                            }
                        } else {
                            System.out.println("No item found! Pleas try again.");
                        }
                    } catch (NegativeInputException | NumberFormatException e) {
                        System.err.println("Please enter the right format of input!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "0" -> {
                    isStop = true;
                }

                default -> System.out.println("Invalid input!");
            }
        }
    }

    public void menu() {
        boolean isStop = false;

        ItemService itemService = ItemService.getInstance();

        MenuService menuService = new MenuService();

        while (!isStop) {
            System.out.println("-- MENU --");
            System.out.println("1. Press 1 to display all menus");
            System.out.println("2. Press 2 to create new menu");
            System.out.println("3. Press 3 to update menu");
            System.out.println("4. Press 4 to delete menu");
            System.out.println("0. Press 0 to return admin page");

            String option = sc.nextLine();

            switch (option) {

                case "1" -> {
                    System.out.println("-- DISPLAY MENU --");

                    HashMap<Integer, Menu> menus = menuService.getAllMenus();

                    for (Integer id : menus.keySet()) {
                        System.out.println(id + ". " + menus.get(id).getName() + " menu:");
                        for (String itemName : itemService.getItemNameByMenuId(id)) {
                            System.out.println("- " + itemName);
                        }
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "2" -> {
                    System.out.println("-- CREATE MENU --");

                    List<Item> items = itemService.getAllItems();

                    List<Integer> itemIdList = new ArrayList<>();

                    for (Item item : items) {
                        System.out.println(item.getId() + ".\t" + item.getName() + "\t($" + item.getPrice() + ")");
                    }

                    System.out.println("Enter the name of menu:");
                    String name = sc.nextLine().trim();

                    try {
                        while (true) {
                            System.out.println("Enter the item id you want to add to new menu:");
                            int itemId = Integer.parseInt(sc.nextLine().trim());

                            if (itemId <= 0) {
                                throw new NegativeInputException();
                            }

                            if (itemId <= items.size()) {
                                Item item = itemService.getItemById(itemId);

                                System.out.println("Item ID: " + item.getId());
                                System.out.println("- Name: " + item.getName());
                                System.out.println("- Price: $" + item.getPrice());

                                itemIdList.add(itemId);

                                System.out.println("More? (y/n)");
                                String choose = sc.nextLine();

                                if (choose.equals("n")) {
                                    Menu menu = new Menu(name);
                                    int menuId = menuService.createMenu(menu);

                                    MenuDetails menuDetails = new MenuDetails(menuId, itemIdList);
                                    menuService.createMenuDetails(menuDetails);

                                    break;
                                } else if (choose.equals("y")) {
                                    System.out.println();
                                } else {
                                    System.out.println("Invalid input!");
                                }
                            }
                        }
                    } catch (NegativeInputException | NumberFormatException e) {
                        System.err.println("Please enter the right format of input!");
                    }
                    System.out.println("-- *-*-*-*-* --");
                }

                case "3" -> {
                    System.out.println("-- UPDATE MENU --");

                    HashMap<Integer, Menu> menus = menuService.getAllMenus();

                    try {
                        System.out.println("Enter the menu id you want to update:");
                        int id = Integer.parseInt(sc.nextLine().trim());

                        if (id <= 0) {
                            throw new NegativeInputException();
                        }

                        if (id <= menus.size()) {
                            List<Integer> itemIdList = new ArrayList<>();

                            Menu menu = menuService.getMenuById(id);

                            System.out.println(id + ". " + menu.getName() + " menu:");
                            for (String itemName : itemService.getItemNameByMenuId(id)) {
                                System.out.println("- " + itemName);
                            }

                            System.out.println("Enter the new name of menu:");
                            String name = sc.nextLine().trim();

                            int i = 0;
                            while (i < itemService.getItemNameByMenuId(id).size()) {
                                System.out.println("Enter the new item id you want to add to menu:");
                                int itemId = Integer.parseInt(sc.nextLine().trim());

                                if (itemId <= 0) {
                                    throw new NegativeInputException();
                                }

                                Item item = itemService.getItemById(itemId);

                                System.out.println("Item ID: " + item.getId());
                                System.out.println("- Name: " + item.getName());
                                System.out.println("- Price: $" + item.getPrice());

                                itemIdList.add(itemId);

                                i++;
                            }
                            Menu newMenu = new Menu(id, name);
                            MenuDetails menuDetails = new MenuDetails(id, itemIdList);
                            if (menuService.updateMenu(newMenu) && menuService.updateMenuDetails(menuDetails)) {
                                System.out.println("Update menu successfully!");
                            }else  {
                                System.out.println("Failed to update! Please try again.");
                            }
                        }
                    } catch (NegativeInputException | NumberFormatException e) {
                        System.err.println("Please enter the right format of input!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "4" -> {
                    System.out.println("-- DELETE MENU --");

                    System.out.println("-- *-*-*-*-* --");
                }

                case "0" -> {
                    isStop = true;
                }

                default -> System.out.println("Invalid input!");
            }
        }
    }
}
