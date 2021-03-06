package greatlearning.week3.day1;

import greatlearning.week3.day1.implementation.UserDAOImpl;
import greatlearning.week3.day1.implementation.UserLogImpl;
import greatlearning.week3.day1.model.Client;
import greatlearning.week3.day1.model.Visitor;
import greatlearning.week3.day1.thread.ClientThread;
import greatlearning.week3.day1.thread.VisitorThread;

import java.util.Scanner;

@SuppressWarnings("unchecked")
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
                String option = sc.nextLine();

                switch (option) {

                    // Register
                    case "1" -> {
                        System.out.println("-- REGISTRATION --");
                        System.out.println("1. Press 1 to register as a client");
                        System.out.println("2. Press 2 to register as a visitor");

                        String registerOption = sc.nextLine();

                        UserDAOImpl userDAO = new UserDAOImpl();

                        if (registerOption.equals("1")) {
                            Client<String, String, String> client = new Client<>();
                            userDAO.register(client);
                            userLog.saveRegisterLog(client.getT2(), "Client");

                        } else if (registerOption.equals("2")) {
                            Visitor<String, String, String> visitor = new Visitor<>();
                            userDAO.register(visitor);
                            userLog.saveRegisterLog(visitor.getT2(), "Visitor");

                        } else {
                            userLog.saveErrorLog("Invalid input");
                            System.out.println("Please try again.");
                        }

                        System.out.println("-- *-*-*-*-* --");
                    }

                    // Login
                    case "2" -> {
                        System.out.println("-- LOGIN --");
                        System.out.println("1. Press 1 to login as a client");
                        System.out.println("2. Press 2 to login as a visitor");

                        String loginOption = sc.nextLine();

                        if (loginOption.equals("1")) {

                            // Client login
                            ClientThread clientThread = new ClientThread();
                            Thread client = new Thread(clientThread.client);
                            client.setName("client");
                            client.start();
                            client.join();

                        } else if (loginOption.equals("2")) {

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
                    case "0" -> {
                        userLog.closeLog();
                        isStop = true;
                        System.out.println("Goodbye, see you later!");
                    }

                    default -> {
                        userLog.saveErrorLog("Invalid input");
                        System.out.println("Invalid option");
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted exception!");
            }
        }
    }
}
