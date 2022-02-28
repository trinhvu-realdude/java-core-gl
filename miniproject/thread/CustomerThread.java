package greatlearning.miniproject.thread;

import greatlearning.miniproject.model.User;
import greatlearning.miniproject.service.BillService;
import greatlearning.miniproject.service.ItemService;
import greatlearning.miniproject.service.OrderService;
import greatlearning.miniproject.exception.NegativeInputException;
import greatlearning.miniproject.model.Bill;
import greatlearning.miniproject.model.Item;
import greatlearning.miniproject.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerThread {
    Scanner sc = new Scanner(System.in);

    private User user;

    public CustomerThread(User user) {
        this.user = user;
    }

    public Runnable customer = () -> {
        System.out.println("-- CUSTOMER OPTION --");

        boolean isStop = false;

        ItemService itemService = ItemService.getInstance();
        OrderService orderService = OrderService.getInstance();
        BillService billService = BillService.getInstance();

        while (!isStop) {
            System.out.println("1. Press 1 to display all items available");
            System.out.println("2. Press 2 to search the item");
            System.out.println("3. Press 3 to create an order");
            System.out.println("4. Press 4 to display your bill");
            System.out.println("0. Press 0 to logout");

            String option = sc.nextLine().trim();

            switch (option) {

                case "1" -> {
                    System.out.println("-- LIST OF ITEMS --");
                    List<Item> items = itemService.getAllItems();

                    for (Item item : items) {
                        System.out.println(item.getId() + ".\t" + item.getName() + "\t($" + item.getPrice() + ")");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

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

                case "3" -> {
                    System.out.println("-- CREATE AN ORDER --");

                    List<Item> items = itemService.getAllItems();
                    List<Integer> itemIdList = new ArrayList<>();

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

                                if (confirm.equals("y")) {

                                    System.out.println("Would you like to order more items? (y/n)");
                                    String check = sc.nextLine().trim();

                                    if (check.equals("n")) {
                                        Order order = new Order(quantity, totalPrice, user.getId());
                                        int orderId = orderService.createOrder(order);

                                        Bill bill = new Bill(orderId, itemIdList);
                                        billService.createBill(bill);

                                        break;
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

                case "4" -> {
                    System.out.println("-- DISPLAY BILLS --");

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
