package greatlearning.week2.day3.implementation;

import greatlearning.week2.day3.model.Client;
import greatlearning.week2.day3.service.UserDAO;
import greatlearning.week2.day3.model.User;
import greatlearning.week2.day3.model.Visitor;

import java.util.Scanner;
import java.util.Stack;

public class UserDAOImpl implements UserDAO {

    private static final Stack<Client> clients = new Stack<>();

    private static final Stack<Visitor> visitors = new Stack<>();

    private static final Scanner sc = new Scanner(System.in);

    @Override
    public void register(User user) {
        if (user instanceof Client) {
            System.out.println("Enter your name: ");
            String name = sc.nextLine().trim();

            System.out.println("Enter your username: ");
            String userName = sc.nextLine().trim();

            System.out.println("Enter your password: ");
            String password = sc.nextLine().trim();

            user.setName(name);
            user.setUserName(userName);
            user.setPassword(password);

            clients.add((Client) user);

            System.out.println("Register client account successfully!");
        }

        if (user instanceof Visitor) {
            System.out.println("Enter your name: ");
            String name = sc.nextLine().trim();

            user.setName(name);

            visitors.add((Visitor) user);

            System.out.println("Register visitor account successfully!");
        }
    }

    @Override
    public int login(String userName, String password) {
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
