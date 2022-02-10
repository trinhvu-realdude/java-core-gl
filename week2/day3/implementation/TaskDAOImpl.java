package greatlearning.week2.day3.implementation;

import greatlearning.week2.Day2.Exception.NegativeInputException;
import greatlearning.week2.day3.service.TaskDAO;
import greatlearning.week2.day3.model.Task;

import java.util.Scanner;
import java.util.Stack;

public class TaskDAOImpl implements TaskDAO {

    private static Stack<Task> tasks = new Stack<>();

    private static final Scanner sc = new Scanner(System.in);

    LogImpl taskLog = new LogImpl();

    public TaskDAOImpl(int size) {
        tasks.setSize(size);
    }

    public TaskDAOImpl() {

    }

    @Override
    public void create() {
        int i = 0;

        while (i < tasks.size()) {
            Task task = new Task();

            System.out.println("Task [" + (i + 1) + "]");

            System.out.println("Enter the task " + (i + 1) + " title:");
            String title = sc.nextLine().trim();

            System.out.println("Enter the task " + (i + 1) + " text:");
            String text = sc.nextLine().trim();

            System.out.println("Enter the task " + (i + 1) + " assigned to:");
            String assignTo = sc.nextLine().trim();

            System.out.println("Enter the completion date to the task " + (i + 1) + " (dd-mm-yy):");
            String completeDate = sc.nextLine().trim();

            if (isValid(completeDate)) {
                task.setId(i + 1);
                task.setTitle(title);
                task.setText(text);
                task.setAssignTo(assignTo);
                task.setCompleteDate(completeDate);
            } else {
                System.out.println("Wrong format of date! Please try again!");
                break;
            }

            tasks.set(i, task);
            i++;
        }
    }

    @Override
    public void update() {
        if (isEmpty(tasks)) {
            System.out.println("Please create a task before updating!");
        } else {
            try {
                System.out.print("Which task do you want to update? (Press task id) ");

                int id = Integer.parseInt(sc.nextLine().trim());
                int index = id - 1;

                if (id <= 0) {
                    throw new NegativeInputException();
                }

                if (id <= tasks.size() && !tasks.get(index).getTitle().equals("Deleted")) {
                    System.out.println("Task [" + id + "]");

                    System.out.println("Enter the new task " + id + " title (" + tasks.get(index).getTitle() + "):");
                    String title = sc.nextLine().trim();

                    System.out.println("Enter the new task " + id + " text (" + tasks.get(index).getText() + "):");
                    String text = sc.nextLine().trim();

                    System.out.println("Enter the new task " + id + " assigned to (" + tasks.get(index).getAssignTo() + "):");
                    String assignTo = sc.nextLine().trim();

                    tasks.get(index).setTitle(title);
                    tasks.get(index).setText(text);
                    tasks.get(index).setAssignTo(assignTo);

                    System.out.println("Update successfully!");
                } else {
                    System.out.println("Task id " + id + " not found!");
                }
            } catch (NegativeInputException | NumberFormatException e) {
                if (e instanceof NegativeInputException) {
                    System.err.println("Negative input exception!");
                } else {
                    System.err.println("Number format exception!");
                }
            }
        }
    }

    @Override
    public void search() {
        if (isEmpty(tasks)) {
            System.out.println("Please create a task before searching!");
        } else {
            System.out.println("Enter the task title you want to search: ");
            String title = sc.nextLine().trim();

            int i = 0;
            boolean checkSearch = false;
            while (i < tasks.size()) {
                if (tasks.get(i).getTitle().equals(title)) {
                    if (!title.equals("Deleted")) {
                        System.out.println("Task [" + tasks.get(i).getId()
                                + "], title: " + tasks.get(i).getTitle()
                                + ", text: " + tasks.get(i).getText()
                                + ", assigned to: " + tasks.get(i).getAssignTo());
                        checkSearch = true;
                    }
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
            try {
                System.out.println("Enter the task id you want to delete: ");
                int number = Integer.parseInt(sc.nextLine().trim());

                if (number <= 0) {
                    throw new NegativeInputException();
                }

                if (number <= tasks.size() && !tasks.get(number - 1).getTitle().equals("Deleted")) {
                    System.out.println("Task [" + tasks.get(number - 1).getId()
                            + "], title: " + tasks.get(number - 1).getTitle()
                            + ", text: " + tasks.get(number - 1).getText()
                            + ", assigned to: " + tasks.get(number - 1).getAssignTo());

                    System.out.print("Are you sure to delete task [" + number + "]? (y/n) ");
                    String sure = sc.nextLine().trim();

                    if (sure.equals("y")) {
                        tasks.get(number - 1).setTitle("Deleted");
                        tasks.get(number - 1).setText("Deleted");
                        tasks.get(number - 1).setAssignTo("Deleted");
                        System.out.println("Delete successfully!");
                    }
                } else {
                    System.out.println("No task found!");
                }
            } catch (NegativeInputException | NumberFormatException e) {
                if (e instanceof NegativeInputException) {
                    System.err.println("Negative input exception!");
                } else {
                    System.err.println("Number format exception!");
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
                            + "] (" + task.getCompleteDate() + ")"
                            + ": " + task.getTitle()
                            + " - " + task.getText()
                            + " - status: " + task.getStatus() + " (assigned to: " + task.getAssignTo() + ")");
                }
            }

            if (count == tasks.size()) {
                System.out.println("List of tasks is empty!");
            }
        }
    }

    public void displayArrangeTask(Stack<Task> list) {
        if (isEmpty(list)) {
            System.out.println("List of tasks is empty!");
        } else {
            int count = 0;
            for (Task task : list) {
                if (task != null) {
                    if (task.getTitle().equals("Deleted")) {
                        count++;
                        continue;
                    }
                    System.out.println("Task [" + task.getId()
                            + "] (" + task.getCompleteDate() + ")"
                            + ": " + task.getTitle()
                            + " - " + task.getText()
                            + " - status: " + task.getStatus() + " (assigned to: " + task.getAssignTo() + ")");
                }
            }

            if (count == list.size()) {
                System.out.println("List of tasks is empty!");
            }
        }
    }

    public void arrange() {
        if (isEmpty(tasks)) {
            System.out.println("List of tasks is empty!");
        } else {
            Stack<Task> cloneTask = new Stack<>();

            cloneTask.addAll(tasks);

            System.out.print("Increasing or decreasing date? (i/d) ");
            String option = sc.nextLine().trim();

            switch (option) {

                case "i" -> {
                    for (int i = 0; i < cloneTask.size(); i++) {
                        int min = i;
                        for (int j = i + 1; j < cloneTask.size(); j++) {
                            if (Integer.parseInt(cloneTask.get(min).getCompleteDate().split("-", 3)[2]) > Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[2])) {
                                min = j;
                            }
                            if (Integer.parseInt(cloneTask.get(min).getCompleteDate().split("-", 3)[2]) == Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[2])) {
                                if (Integer.parseInt(cloneTask.get(min).getCompleteDate().split("-", 3)[1]) > Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[1])) {
                                    min = j;
                                }
                                if (Integer.parseInt(cloneTask.get(min).getCompleteDate().split("-", 3)[1]) == Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[1])) {
                                    if (Integer.parseInt(cloneTask.get(min).getCompleteDate().split("-", 3)[0]) > Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[0])) {
                                        min = j;
                                    }
                                }
                            }
                        }
                        Task temp = cloneTask.get(min);
                        cloneTask.set(min, cloneTask.get(i));
                        cloneTask.set(i, temp);
                    }

                    displayArrangeTask(cloneTask);
                }

                case "d" -> {
                    for (int i = 0; i < cloneTask.size(); i++) {
                        int max = i;
                        for (int j = i + 1; j < cloneTask.size(); j++) {
                            if (Integer.parseInt(cloneTask.get(max).getCompleteDate().split("-", 3)[2]) < Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[2])) {
                                max = j;
                            }
                            if (Integer.parseInt(cloneTask.get(max).getCompleteDate().split("-", 3)[2]) == Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[2])) {
                                if (Integer.parseInt(cloneTask.get(max).getCompleteDate().split("-", 3)[1]) < Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[1])) {
                                    max = j;
                                }
                                if (Integer.parseInt(cloneTask.get(max).getCompleteDate().split("-", 3)[1]) == Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[1])) {
                                    if (Integer.parseInt(cloneTask.get(max).getCompleteDate().split("-", 3)[0]) < Integer.parseInt(cloneTask.get(j).getCompleteDate().split("-", 3)[0])) {
                                        max = j;
                                    }
                                }
                            }
                        }
                        Task temp = cloneTask.get(max);
                        cloneTask.set(max, cloneTask.get(i));
                        cloneTask.set(i, temp);
                    }

                    displayArrangeTask(cloneTask);
                }

                default -> System.out.println("Please try again!");
            }
        }
    }

    public void displayAssignedTo(String name) {
        if (isEmpty(tasks)) {
            System.out.println("You don't have any tasks to do!");
        } else {
            int count = 0;
            for (Task task : tasks) {
                if (task.getAssignTo().equals(name)) {
                    System.out.println("Task [" + task.getId()
                            + "] (" + task.getCompleteDate() + ")"
                            + ": " + task.getTitle()
                            + " - " + task.getText()
                            + " - status: " + task.getStatus());
                    count++;
                }
            }

            if (count == 0) {
                System.out.println("List of tasks assigned to you is empty!");
            }
        }
    }

    public void changeStatus(String name) {
        if (isEmpty(tasks)) {
            System.out.println("You don't have any tasks to do!");
        } else {
            try {
                System.out.println("Which task do you want to change status? (Press task id) ");

                int id = Integer.parseInt(sc.nextLine().trim());
                int index = id - 1;

                if (id <= 0) {
                    throw new NegativeInputException();
                }

                if (id <= tasks.size() && !tasks.get(index).getTitle().equals("Deleted") && tasks.get(index).getAssignTo().equals(name)) {
                    if (tasks.get(index).getStatus().equals("Incomplete")) {
                        System.out.println("Task [" + id + "] (" + tasks.get(index).getCompleteDate() + ")"
                                + ": " + tasks.get(index).getTitle()
                                + " - " + tasks.get(index).getText());

                        System.out.print("Have you completed the task " + id + "? (y/n) ");
                        String option = sc.nextLine().trim();

                        if (option.equals("y")) {
                            tasks.get(index).setStatus("Completed");
                            System.out.println("Great job! Keep your momentum!");
                        }
                    } else {
                        System.out.println("You already have completed this task! Good job!");
                    }
                } else {
                    System.out.println("Task id " + id + " not found!");
                }
            } catch (NegativeInputException | NumberFormatException e) {
                if (e instanceof NegativeInputException) {
                    System.err.println("Negative input exception!");
                } else {
                    System.err.println("Number format exception!");
                }
            }
        }
    }

    public void displayTaskByStatus(String name, String status) {
        if (isEmpty(tasks)) {
            System.out.println("You don't have any tasks to do!");
        } else {
            int count = 0;
            for (Task task : tasks) {
                if (task.getAssignTo().equals(name) && task.getStatus().equals(status)) {
                    System.out.println("Task [" + task.getId()
                            + "] (" + task.getCompleteDate() + ")"
                            + ": " + task.getTitle()
                            + " - " + task.getText()
                            + " - status: " + task.getStatus());
                    count++;
                }
            }

            if (count == 0) {
                System.out.println("List of tasks is empty!");
            }
        }
    }

    public boolean isEmpty(Stack<Task> tasks) {
        int checkEmpty = 0;
        int checkDeleted = 0;

        while (checkEmpty < tasks.size()) {
            if (tasks.get(checkEmpty) == null) {
                break;
            }
            checkEmpty++;
        }

        int i = 0;

        while (i < tasks.size()) {
            if (checkEmpty != 0) {
                if (tasks.get(i).getTitle().equals("Deleted")) {
                    checkDeleted++;
                }
            }
            i++;
        }

        return checkEmpty == 0 || checkDeleted == tasks.size();
    }

    public boolean isValid(String completeDate) {
        String[] date = completeDate.split("-", 3);

        if (date.length == 3) {
            int d = Integer.parseInt(date[0]);
            int m = Integer.parseInt(date[1]);
            int y = Integer.parseInt(date[2]);

            return (d > 0 && d <= 31) && (m > 0 && m <= 12) && (y > 2000);
        }
        return false;
    }

    public int getLength() {
        if (tasks.size() == 0) {
            return 0;
        }
        return tasks.size();
    }
}
