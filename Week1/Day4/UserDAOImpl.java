package GreatLearning.Week1.Day4;

import java.util.Scanner;

public class UserDAOImpl implements UserDAO {

    private static final Client[] clients = new Client[10];

    private static final Visitor[] visitors = new Visitor[10];

    private static final Scanner sc = new Scanner(System.in);

    static int countClient;

    static int countVisitor;

    @Override
    public void register(User user) {
        if (user instanceof Client) {
            System.out.println("Enter your name: ");
            String name = sc.nextLine();

            System.out.println("Enter your username: ");
            String userName = sc.nextLine();

            System.out.println("Enter your password: ");
            String password = sc.nextLine();

            user.setName(name);
            user.setUserName(userName);
            user.setPassword(password);

            clients[countClient] = (Client) user;
            countClient++;

            System.out.println("Register client account successfully!");
        }

        if (user instanceof Visitor) {
            System.out.println("Enter your name: ");
            String name = sc.nextLine();

            user.setName(name);

            visitors[countVisitor] = (Visitor) user;
            countVisitor++;

            System.out.println("Register visitor account successfully!");
        }
    }

    @Override
    public int login() {
        System.out.println("Username: ");
        String userName = sc.nextLine();

        System.out.println("Password: ");
        String password = sc.nextLine();

        for (Client client : clients) {
            if (client == null) {
                break;
            } else {
                if (client.getUserName().equals(userName) && client.getPassword().equals(password)) {
                    System.out.println("Login successfully!");
                    System.out.println("-- Hi, " + client.getUserName() + "! --");
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public int login(String name) {
        for (Visitor visitor : visitors) {
            if (visitor == null) {
                break;
            } else {
                if (visitor.getName().equals(name)) {
                    System.out.println("Login successfully!");
                    System.out.println("-- Hi, " + visitor.getName() + "! --");
                    return 1;
                }
            }
        }
        return 0;
    }
}
