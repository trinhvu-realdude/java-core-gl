package greatlearning.week2.day5;

import greatlearning.week2.day5.implementation.UserDAOImpl;
import greatlearning.week2.day5.implementation.UserLogImpl;
import greatlearning.week2.day5.model.Client;
import greatlearning.week2.day5.model.Visitor;
import greatlearning.week2.day5.thread.ClientThread;
import greatlearning.week2.day5.thread.VisitorThread;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        UserLogImpl userLog = new UserLogImpl();
        userLog.startLog();

        System.out.println("-- TO DO MANAGER --");

        boolean isStop = false;

        while (!isStop) {
            System.out.println("1. Press 1 to register");
            System.out.println("2. Press 2 to login");
            System.out.println("0. Press 0 to exit");

            try {
                String input = sc.nextLine();
                int option = Integer.parseInt(input);

                switch (option) {

                    // Register
                    case 1 -> {
                        System.out.println("-- REGISTRATION --");
                        System.out.println("1. Press 1 to register as a client");
                        System.out.println("2. Press 2 to register as a visitor");

                        String in = sc.nextLine();
                        int registerOption = Integer.parseInt(in);

                        UserDAOImpl userDAO = new UserDAOImpl();

                        if (registerOption == 1) {
                            Client client = new Client();
                            userDAO.register(client);
                            userLog.saveRegisterLog(client.getUserName(), "Client");

                        } else if (registerOption == 2) {
                            Visitor visitor = new Visitor();
                            userDAO.register(visitor);
                            userLog.saveRegisterLog(visitor.getName(), "Visitor");

                        } else {
                            userLog.saveErrorLog("Invalid input");
                            System.out.println("Please try again.");
                        }

                        System.out.println("-- *-*-*-*-* --");
                    }

                    // Login
                    case 2 -> {
                        System.out.println("-- LOGIN --");
                        System.out.println("1. Press 1 to login as a client");
                        System.out.println("2. Press 2 to login as a visitor");

                        String in = sc.nextLine();
                        int loginOption = Integer.parseInt(in);

                        if (loginOption == 1) {

                            // Client login
                            ClientThread clientThread = new ClientThread();
                            Thread client = new Thread(clientThread.client);
                            client.setName("client");
                            client.start();
                            client.join();

                        } else if (loginOption == 2) {

                            // Visitor login
                            VisitorThread visitorThread = new VisitorThread();
                            Thread visitor = new Thread(visitorThread.visitor);
                            visitor.setName("visitor");
                            visitor.start();
                            visitor.join();

                        } else {
                            userLog.saveErrorLog("Invalid input");
                            System.out.println("Please try again.");
                        }

                        System.out.println("-- *-*-*-*-* --");
                    }

                    // Exit
                    case 0 -> {
                        userLog.closeLog();
                        isStop = true;
                        System.out.println("Goodbye, see you later!");
                    }

                    default -> {
                        userLog.saveErrorLog("Invalid input");
                        System.out.println("Invalid option");
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Number format exception!");
            }
            catch (InterruptedException e) {
                System.err.println("Interrupted exception!");
            }
        }
    }
}
