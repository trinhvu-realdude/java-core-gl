package greatlearning.week1.Day4;

import java.util.Scanner;

public class TaskDAOImpl implements TaskDAO {

    private static Task[] tasks;

    private static final Scanner sc = new Scanner(System.in);

    public TaskDAOImpl(int size) {
        tasks = new Task[size];
    }

    public TaskDAOImpl() {

    }

    @Override
    public void create() {
        int i = 0;

        while (i < tasks.length) {
            Task task = new Task();

            System.out.println("Task [" + (i + 1) + "]");

            System.out.println("Enter the task " + (i + 1) + " title:");
            String title = sc.nextLine();

            System.out.println("Enter the task " + (i + 1) + " text:");
            String text = sc.nextLine();

            System.out.println("Enter the task " + (i + 1) + " assigned to:");
            String assignTo = sc.nextLine();

            task.setId(i + 1);
            task.setTitle(title);
            task.setText(text);
            task.setAssignTo(assignTo);

            tasks[i] = task;
            i++;
        }
    }

    @Override
    public void update() {
        if (isEmpty(tasks)) {
            System.out.println("Please create a task before updating!");
        } else {
            System.out.print("Which task do you want to update? (Press task id) ");
            String input = sc.nextLine();
            int id = Integer.parseInt(input);

            if (id <= tasks.length) {
                int index = id - 1;

                System.out.println("Task [" + id + "]");

                System.out.println("Enter the new task " + id + " title (" + tasks[index].getTitle() + "):");
                String title = sc.nextLine();

                System.out.println("Enter the new task " + id + " text (" + tasks[index].getText() + "):");
                String text = sc.nextLine();

                System.out.println("Enter the new task " + id + " assigned to (" + tasks[index].getAssignTo() + "):");
                String assignTo = sc.nextLine();

                tasks[index].setTitle(title);
                tasks[index].setText(text);
                tasks[index].setAssignTo(assignTo);

                System.out.println("Update successfully!");
            } else {
                System.out.println("No task found");
            }
        }
    }

    @Override
    public void search() {
        if (isEmpty(tasks)) {
            System.out.println("Please create a task before searching!");
        } else {
            System.out.println("Enter the task title you want to search: ");
            String title = sc.nextLine();

            int i = 0;
            boolean checkSearch = false;
            while (i < tasks.length) {
                if (tasks[i].getTitle().equals(title)) {
                    System.out.println("Task [" + tasks[i].getId()
                            + "], title: " + tasks[i].getTitle()
                            + ", text: " + tasks[i].getText()
                            + ", assigned to: " + tasks[i].getAssignTo());
                    checkSearch = true;
                }
                i++;
            }

            if (!checkSearch) {
                System.out.println("Task " + title + " does not exist!");
            }
        }
    }

    @Override
    public void delete() {
        if (isEmpty(tasks)) {
            System.out.println("Please create a task before deleting!");
        } else {
            System.out.println("Enter the task id you want to delete: ");
            int number = sc.nextInt();

            System.out.println("Task [" + tasks[number - 1].getId()
                    + "], title: " + tasks[number - 1].getTitle()
                    + ", text: " + tasks[number - 1].getText()
                    + ", assigned to: " + tasks[number - 1].getAssignTo());

            System.out.print("Are you sure to delete task [" + number + "]? (y/n) ");
            String sure = sc.next();

            if (sure.equals("y")) {
                if (number <= tasks.length) {
                    tasks[number - 1].setTitle("Deleted");
                    tasks[number - 1].setText("Deleted");
                    tasks[number - 1].setAssignTo("Deleted");
                    System.out.println("Delete successfully!");
                } else {
                    System.out.println("No task found!");
                }
            }
        }
    }

    @Override
    public void display() {
        if (isEmpty(tasks)) {
            System.out.println("List of tasks is empty!");
        } else {
            int count = 0;
            for (Task task : tasks) {
                if (task != null) {
                    if (task.getTitle().equals("Deleted")) {
                        count++;
                        continue;
                    }
                    System.out.println("Task [" + task.getId()
                            + "], title: " + task.getTitle()
                            + ", text: " + task.getText()
                            + ", assigned to: " + task.getAssignTo());
                }
            }

            if (count == tasks.length) {
                System.out.println("List of tasks is empty!");
            }
        }
    }

    public void displayAssignedTo(String name) {
        if (isEmpty(tasks)) {
            System.out.println("List of tasks is empty!");
        } else {
            int count = 0;
            for (Task task : tasks) {
                if (task.getAssignTo().equals(name)) {
                    System.out.println("Task [" + task.getId()
                            + "], title: " + task.getTitle()
                            + ", text: " + task.getText()
                            + ", assigned to: " + task.getAssignTo());
                    count++;
                }
            }

            if (count == 0) {
                System.out.println("List of tasks assigned to you is empty!");
            }
        }
    }

    public static boolean isEmpty(Task[] tasks) {
        int checkEmpty = 0;
        int checkDeleted = 0;

        while (checkEmpty < tasks.length) {
            if (tasks[checkEmpty] == null) {
                break;
            }
            checkEmpty++;
        }

        int i = 0;

        if (checkEmpty != 0) {
            while (i < tasks.length) {
                if (tasks[i].getTitle().equals("Deleted")) {
                    checkDeleted++;
                }
                i++;
            }
        }

        return checkEmpty == 0 || checkDeleted == tasks.length;
    }
}
