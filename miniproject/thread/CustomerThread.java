package greatlearning.miniproject.thread;

import greatlearning.miniproject.model.*;
import greatlearning.miniproject.service.ItemService;
import greatlearning.miniproject.service.MenuService;
import greatlearning.miniproject.service.OrderService;
import greatlearning.miniproject.exception.NegativeInputException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CustomerThread {
    Scanner sc = new Scanner(System.in);

    private User user;

    public CustomerThread(User user) {
        this.user = user;
    }

    public Runnable customer = () -> {
        boolean isStop = false;

        ItemService itemService = ItemService.getInstance();
        OrderService orderService = OrderService.getInstance();
        MenuService menuService = MenuService.getInstance();

        while (!isStop) {
            System.out.println("-- CUSTOMER OPTION --");
            System.out.println("1. Press 1 to display all items available");
            System.out.println("2. Press 2 to search the item");
            System.out.println("3. Press 3 to create an order");
            System.out.println("4. Press 4 to display your bill");
            System.out.println("5. Press 5 to display all menus of restaurant");
            System.out.println("0. Press 0 to logout");

            String option = sc.nextLine().trim();

            switch (option) {

                // Display all items available
                case "1" -> {
                    System.out.println("-- LIST OF ITEMS --");
                    List<Item> items = itemService.getAllItems();

                    for (Item item : items) {
                        System.out.println(item.getId() + ".\t" + item.getName() + "\t($" + item.getPrice() + ")");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                // Search the item
                case "2" -> {
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

                // Create an order
                case "3" -> {
                    System.out.println("-- CREATE AN ORDER --");

                    List<Item> items = itemService.getAllItems();
                    List<Integer> itemIdList = new ArrayList<>();
                    List<Integer> numberOfPlatesList = new ArrayList<>();

                    int quantity = 0;
                    double totalPrice = 0;

                    try {
                        while (true) {
                            System.out.println("Enter the item id you want to order:");
                            int itemId = Integer.parseInt(sc.nextLine().trim());

                            System.out.println("Enter the number of plates per item:");
                            int numberOfPlates = Integer.parseInt(sc.nextLine().trim());

                            if (itemId <= 0 || numberOfPlates <= 0) {
                                throw new NegativeInputException();
                            }

                            quantity++;

                            if (itemId <= items.size()) {
                                Item item = itemService.getItemById(itemId);

                                System.out.println("-> " + numberOfPlates + " plates of "
                                        + item.getName() + " ($"
                                        + String.format("%.2f", (item.getPrice() * numberOfPlates)) + ")");

                                System.out.println("Please confirm this item! (y/n)");
                                String confirm = sc.nextLine().trim();

                                totalPrice += item.getPrice() * numberOfPlates;
                                itemIdList.add(itemId);
                                numberOfPlatesList.add(numberOfPlates);

                                if (confirm.equals("y")) {

                                    System.out.println("Would you like to order more items? (y/n)");
                                    String check = sc.nextLine().trim();

                                    if (check.equals("n")) {
                                        Order order = new Order(quantity, totalPrice, user.getId());
                                        int orderId = orderService.createOrder(order);

                                        OrderDetails orderDetails = new OrderDetails(orderId, itemIdList, numberOfPlatesList);
                                        orderService.createOrderDetails(orderDetails);

                                        break;
                                    } else if (check.equals("y")) {
                                        System.out.println("-----------------");
                                    } else {
                                        System.out.println("Invalid input!");
                                    }
                                } else {
                                    System.out.println("Please try again!");
                                    System.out.println("-----------------");
                                }
                            } else {
                                System.out.println("No item found! Pleas try again.");
                                break;
                            }
                        }
                    } catch (NegativeInputException | NumberFormatException e) {
                        System.err.println("Please enter the right format of input!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                // Display your bill
                case "4" -> {
                    System.out.println("-- DISPLAY BILLS --");
                    HashMap<Integer, Bill> bills = orderService.getBillByUserId(user.getId());

                    for (Integer id : bills.keySet()) {
                        System.out.println("[" + bills.get(id).getDate() + "] Bill ID: " + id + " (quantity: " + bills.get(id).getQuantity() + ")");
                        for (Integer orderDetailId : itemService.getItemsByOrderId(id).keySet()) {
                            System.out.println("- " + itemService.getItemsByOrderId(id).get(orderDetailId).getName()
                                    + " ($" + itemService.getItemsByOrderId(id).get(orderDetailId).getPrice() + "): "
                                    + orderService.getNumberOfPlatesById(orderDetailId) + " plates");
                        }
                        System.out.println("-> Total: $" + bills.get(id).getTotal());
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "5" -> {
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

                case "0" -> {
                    isStop = true;
                    System.out.println("Logout successfully!");
                }

                default -> System.out.println("Invalid option");
            }
        }
    };
}
