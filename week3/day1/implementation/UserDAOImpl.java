package greatlearning.week3.day1.implementation;

import greatlearning.week3.day1.model.Client;
import greatlearning.week3.day1.model.User;
import greatlearning.week3.day1.model.Visitor;
import greatlearning.week3.day1.service.UserDAO;

import java.util.ArrayList;
import java.util.Scanner;

public class UserDAOImpl implements UserDAO {

    private static final ArrayList<Client<String, String, String>> clients = new ArrayList<>();

    private static final ArrayList<Visitor<String, String, String>> visitors = new ArrayList<>();

    Scanner sc = new Scanner(System.in);

    @Override
    public void register(User<String, String, String> user) {
        if (user instanceof Client) {
            System.out.println("Enter your name: ");
            String name = sc.nextLine().trim();

            System.out.println("Enter your username: ");
            String userName = sc.nextLine().trim();

            System.out.println("Enter your password: ");
            String password = sc.nextLine().trim();

            user.setT1(name);
            user.setT2(userName);
            user.setT3(password);

            clients.add((Client<String, String, String>) user);

            System.out.println("Register client account successfully!");
        }

        if (user instanceof Visitor) {
            System.out.println("Enter your name: ");
            String name = sc.nextLine().trim();

            user.setT1(name);

            visitors.add((Visitor<String, String, String>) user);

            System.out.println("Register visitor account successfully!");
        }
    }

    @Override
    public int login(String userName, String password) {
        for (Client<String, String, String> client : clients) {
            if (client.getT2().equals(userName) && client.getT3().equals(password)) {
                System.out.println("Login successfully!");
                System.out.println("-- Hi, " + client.getT2() + "! --");
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int login(String name) {
        for (Visitor<String, String, String> visitor : visitors) {
            if (visitor.getT1().equals(name)) {
                System.out.println("Login successfully!");
                System.out.println("-- Hi, " + visitor.getT1() + "! --");
                return 1;
            }
        }
        return 0;
    }
}
