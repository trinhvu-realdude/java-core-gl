package greatlearning.miniproject.thread;

import java.util.Scanner;

public class CustomerThread {
    Scanner sc = new Scanner(System.in);

    public Runnable customer = () -> {
        System.out.println("-- CUSTOMER OPTION --");

        boolean isStop = false;

        while (!isStop) {
            System.out.println("1. Press 1 to display all the items available");
            System.out.println("2. Press 2 to select the item you want to order");
            System.out.println("3. Press 3 to ");
        }
    };
}
