package greatlearning.miniproject.app;

import greatlearning.miniproject.service.UserService;
import greatlearning.miniproject.exception.WrongCredentialsException;
import greatlearning.miniproject.model.User;
import greatlearning.miniproject.thread.AdminThread;
import greatlearning.miniproject.thread.CustomerThread;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserService userService = UserService.getInstance();

        boolean isStop = false;

        while (!isStop) {
            System.out.println("-- SURABI BILLING SYSTEM --");
            System.out.println("1. Press 1 to register");
            System.out.println("2. Press 2 to login");
            System.out.println("0. Press 0 to exit");

            String option = sc.nextLine().trim();

            switch (option) {

                // Registration
                case "1" -> {
                    System.out.println("-- REGISTRATION --");

                    System.out.println("Enter your username:");
                    String userName = sc.nextLine().trim();

                    System.out.println("Enter your password:");
                    String password = sc.nextLine().trim();

                    System.out.println("Confirm your password:");
                    String confirmPassword = sc.nextLine().trim();

                    if (password.equals(confirmPassword)) {

                        User user = new User.UserBuilder(userName, password).setRoleId(2).build();

                        // Using register function of User service
                        userService.register(user);
                    } else {
                        System.out.println("Please try again!");
                    }
                }

                // Login
                case "2" -> {
                    System.out.println("-- LOGIN --");

                    try {
                        System.out.println("Username:");
                        String userName = sc.nextLine().trim();

                        System.out.println("Password:");
                        String password = sc.nextLine().trim();

                        // Using login function of User service
                        User user = userService.login(userName, password);

                        if (user != null) { // if result from login function is not equal null, this block is executed

                            System.out.println("Login successfully");
                            System.out.println("-- Hi, " + user.getUserName() + "! --");

                            // Start Admin thread if roleId = 1
                            if (user.getRoleId() == 1) {
                                AdminThread adminThread = new AdminThread();
                                Thread admin = new Thread(adminThread.admin);
                                admin.setName("admin");
                                admin.start();
                                admin.join();
                            }

                            // Start Customer thread if roleId = 2
                            if (user.getRoleId() == 2) {
                                CustomerThread customerThread = new CustomerThread(user);
                                Thread customer = new Thread(customerThread.customer);
                                customer.setName("customer");
                                customer.start();
                                customer.join();
                            }
                        } else { // login function return null, throw new wrong credentials exception
                            throw new WrongCredentialsException();
                        }
                    } catch (WrongCredentialsException e) {
                        System.err.println("Credentials wrong! Please try again.");
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted exception!");
                    }
                }

                // Logout
                case "0" -> {
                    isStop = true;
                    System.out.println("Goodbye, see you again!");
                }

                default -> System.out.println("Invalid option");
            }
        }
    }
}
