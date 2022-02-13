package greatlearning.week2.day5.implementation;

import greatlearning.week2.day5.exception.NegativeInputException;
import greatlearning.week2.day5.model.Task;
import greatlearning.week2.day5.service.TaskDAO;

import java.util.Scanner;
import java.util.Stack;
import java.util.function.Consumer;

public class TaskDAOImpl implements TaskDAO {

    static Stack<Task> tasks = new Stack<>();

    Scanner sc = new Scanner(System.in);

    UserLogImpl userLog = new UserLogImpl();

    Consumer<Task> consumer = (task) -> System.out.println(
            "Task [" + task.getId()
                    + "] (" + task.getCompleteDate() + ")"
                    + ": " + task.getTitle()
                    + " - " + task.getText()
                    + " - status: " + task.getStatus()
                    + " (assigned to: " + task.getAssignTo() + ")"
    );

    public TaskDAOImpl(int size) {
        tasks.setSize(size);
    }

    public TaskDAOImpl() {

    }

    @Override
    public void create(String userName) {
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

                userLog.saveCreateTaskLog(userName, title);
            } else {
                userLog.saveErrorLog("Wrong format of date");
                System.out.println("Wrong format of date! Please try again!");
                break;
            }

            tasks.set(i, task);
            i++;
        }
    }

    @Override
    public void update(String userName) {
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

                    System.out.println("Enter the new task " + id + " complete date (" + tasks.get(index).getCompleteDate() + "):");
                    String completeDate = sc.nextLine().trim();

                    if (isValid(completeDate)) {
                        tasks.get(index).setTitle(title);
                        tasks.get(index).setText(text);
                        tasks.get(index).setAssignTo(assignTo);
                        tasks.get(index).setCompleteDate(completeDate);

                        System.out.println("Update successfully!");

                        userLog.saveUpdateTaskLog(userName, id);
                    } else {
                        userLog.saveErrorLog("Wrong format of date");
                        System.out.println("Wrong format of date! Please try again!");
                    }
                } else {
                    userLog.saveErrorLog("Task not found");
                    System.out.println("Task id " + id + " not found!");
                }
            } catch (NegativeInputException | NumberFormatException e) {
                userLog.saveErrorLog("Invalid input");
                System.err.println("Please enter the positive number!");
            }
        }
    }

    @Override
    public void search(String userName) {
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
                        consumer.accept(tasks.get(i));
                        checkSearch = true;
                    }
                }
                i++;
            }
            if (!checkSearch) {
                userLog.saveErrorLog("Task not found");
                System.out.println("Task " + title + " does not exist!");
            } else {
                userLog.saveSearchTaskLog(userName, title);
            }
        }
    }

    @Override
    public void delete(String userName) {
        if (isEmpty(tasks)) {
            System.out.println("Please create a task before deleting!");
        } else {
            try {
                System.out.println("Enter the task id you want to delete: ");
                int id = Integer.parseInt(sc.nextLine().trim());

                if (id <= 0) {
                    throw new NegativeInputException();
                }

                if (id <= tasks.size() && !tasks.get(id - 1).getTitle().equals("Deleted")) {
                    System.out.println(tasks.get(id - 1).toString());

                    System.out.print("Are you sure to delete task [" + id + "]? (y/n) ");
                    String sure = sc.nextLine().trim();

                    if (sure.equals("y")) {
                        tasks.get(id - 1).setTitle("Deleted");
                        tasks.get(id - 1).setText("Deleted");
                        tasks.get(id - 1).setAssignTo("Deleted");
                        System.out.println("Delete successfully!");

                        userLog.saveDeleteTaskLog(userName, id);
                    }
                } else {
                    userLog.saveErrorLog("Task not found");
                    System.out.println("No task found!");
                }
            } catch (NegativeInputException | NumberFormatException e) {
                userLog.saveErrorLog("Invalid input");
                System.err.println("Please enter the positive number!");
            }
        }
    }

    @Override
    public void display(String userName) {
        if (isEmpty(tasks)) {
            System.out.println("List of tasks is empty!");
        } else {
            tasks.forEach((task) -> {
                if (!task.getTitle().equals("Deleted")) {
                    consumer.accept(task);
                }
            });
        }
    }

    public void displayArrangeTask(Stack<Task> list) {
        if (isEmpty(list)) {
            System.out.println("List of tasks is empty!");
        } else {
            list.forEach((task) -> {
                if (!task.getTitle().equals("Deleted")) {
                    consumer.accept(task);
                }
            });
        }
    }

    public void arrange(String userName) {
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

                    userLog.saveArrangeTaskLog(userName, "increasing");

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

                    userLog.saveArrangeTaskLog(userName, "decreasing");

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
                    consumer.accept(task);
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

                            userLog.saveChangeStatusLog(name, id);
                        }
                    } else {
                        System.out.println("You already have completed this task! Good job!");
                    }
                } else {
                    userLog.saveErrorLog("Task not found");
                    System.out.println("Task id " + id + " not found!");
                }
            } catch (NegativeInputException | NumberFormatException e) {
                userLog.saveErrorLog("Invalid input");
                System.err.println("Please enter the positive number!");
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
                    consumer.accept(task);
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

        if (checkEmpty == 0 || checkDeleted == tasks.size()) {
            userLog.saveErrorLog("Empty list");
            return true;
        }

        return false;
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
