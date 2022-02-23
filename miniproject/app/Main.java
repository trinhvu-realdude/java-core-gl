package greatlearning.miniproject.app;

import greatlearning.miniproject.dao.UserDAOImpl;
import greatlearning.miniproject.exception.WrongCredentialsException;
import greatlearning.miniproject.model.User;
import greatlearning.miniproject.thread.AdminThread;
import greatlearning.miniproject.thread.CustomerThread;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static UserDAOImpl userDAO = UserDAOImpl.getInstance();

    public static void main(String[] args) {

        System.out.println("-- SURABI BILLING SYSTEM --");

        boolean isStop = false;

        while (!isStop) {
            System.out.println("1. Press 1 to register");
            System.out.println("2. Press 2 to login");
            System.out.println("0. Press 0 to exit");

            String option = sc.nextLine().trim();

            switch (option) {

                case "1" -> {
                    System.out.println("-- REGISTRATION --");
                    System.out.println("1. Press 1 to register as a admin");
                    System.out.println("2. Press 2 to register as an customer");

                    String registerOption = sc.nextLine().trim();

                    if (registerOption.equals("1")) {
                        // Admin registration
                        System.out.println("-- ADMIN --");

                        registerFields(1);
                    } else if (registerOption.equals("2")) {
                        // Customer registration
                        System.out.println("-- CUSTOMER --");

                        registerFields(2);
                    } else {
                        System.out.println("Please try again!");
                    }
                }

                case "2" -> {
                    System.out.println("-- LOGIN --");

                    try {
                        System.out.println("Username:");
                        String userName = sc.nextLine();

                        System.out.println("Password:");
                        String password = sc.nextLine();

                        User user = userDAO.login(userName, password);

                        if (user != null) {

                            // Admin thread
                            if (user.getRoleId() == 1) {
                                System.out.println("Login successfully");
                                System.out.println("-- Hi, " + user.getUserName() + "! --");

                                AdminThread adminThread = new AdminThread();
                                Thread admin = new Thread(adminThread.admin);
                                admin.setName("admin");
                                admin.start();
                                admin.join();
                            }

                            // Customer thread
                            if (user.getRoleId() == 2) {
                                System.out.println("Login successfully");
                                System.out.println("-- Hi, " + user.getUserName() + "! --");

                                CustomerThread customerThread = new CustomerThread();
                                Thread customer = new Thread(customerThread.customer);
                                customer.setName("customer");
                                customer.start();
                                customer.join();
                            }
                        } else {
                            throw new WrongCredentialsException();
                        }
                    } catch (WrongCredentialsException e) {
                        System.err.println("Credentials wrong! Please try again.");
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted exception!");
                    }
                }

                case "0" -> {
                    isStop = true;
                    System.out.println("Goodbye, see you again!");
                }

                default -> System.out.println("Invalid option");
            }
        }
    }

    public static void registerFields(int roleId) {
        System.out.println("Enter your username:");
        String userName = sc.nextLine().trim();

        System.out.println("Enter your password:");
        String password = sc.nextLine().trim();

        System.out.println("Confirm your password:");
        String confirmPassword = sc.nextLine().trim();

        if (password.equals(confirmPassword)) {
            User user = new User(userName, password, roleId);

            userDAO.register(user);
        } else {
            System.out.println("Please try again!");
        }
    }
}
