package greatlearning.week2.day5.implementation;

import greatlearning.week2.day5.model.Client;
import greatlearning.week2.day5.model.User;
import greatlearning.week2.day5.model.Visitor;
import greatlearning.week2.day5.service.UserDAO;

import java.util.ArrayList;
import java.util.Scanner;

public class UserDAOImpl implements UserDAO {

    private static final ArrayList<Client> clients = new ArrayList<>();

    private static final ArrayList<Visitor> visitors = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

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
            if (client.getUserName().equals(userName) && client.getPassword().equals(password)) {
                System.out.println("Login successfully!");
                System.out.println("-- Hi, " + client.getUserName() + "! --");
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int login(String name) {
        for (Visitor visitor : visitors) {
            if (visitor.getName().equals(name)) {
                System.out.println("Login successfully!");
                System.out.println("-- Hi, " + visitor.getName() + "! --");
                return 1;
            }
        }
        return 0;
    }
}
