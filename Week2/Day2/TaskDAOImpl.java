package GreatLearning.Week2.Day2;

import GreatLearning.Week2.Day2.Exception.NegativeInputException;

import java.util.Scanner;

public class TaskDAOImpl implements TaskDAO {

    private static Task[] tasks = new Task[0];

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

            tasks[i] = task;
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

                if (id <= tasks.length && !tasks[index].getTitle().equals("Deleted")) {
                    System.out.println("Task [" + id + "]");

                    System.out.println("Enter the new task " + id + " title (" + tasks[index].getTitle() + "):");
                    String title = sc.nextLine().trim();

                    System.out.println("Enter the new task " + id + " text (" + tasks[index].getText() + "):");
                    String text = sc.nextLine().trim();

                    System.out.println("Enter the new task " + id + " assigned to (" + tasks[index].getAssignTo() + "):");
                    String assignTo = sc.nextLine().trim();

                    tasks[index].setTitle(title);
                    tasks[index].setText(text);
                    tasks[index].setAssignTo(assignTo);

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
            while (i < tasks.length) {
                if (tasks[i].getTitle().equals(title)) {
                    if (!title.equals("Deleted")) {
                        System.out.println("Task [" + tasks[i].getId()
                                + "], title: " + tasks[i].getTitle()
                                + ", text: " + tasks[i].getText()
                                + ", assigned to: " + tasks[i].getAssignTo());
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

                if (number <= tasks.length && !tasks[number - 1].getTitle().equals("Deleted")) {
                    System.out.println("Task [" + tasks[number - 1].getId()
                            + "], title: " + tasks[number - 1].getTitle()
                            + ", text: " + tasks[number - 1].getText()
                            + ", assigned to: " + tasks[number - 1].getAssignTo());

                    System.out.print("Are you sure to delete task [" + number + "]? (y/n) ");
                    String sure = sc.nextLine().trim();

                    if (sure.equals("y")) {
                        tasks[number - 1].setTitle("Deleted");
                        tasks[number - 1].setText("Deleted");
                        tasks[number - 1].setAssignTo("Deleted");
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

            if (count == tasks.length) {
                System.out.println("List of tasks is empty!");
            }
        }
    }

    public void displayArrangeTask(Task[] list) {
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

            if (count == list.length) {
                System.out.println("List of tasks is empty!");
            }
        }
    }

    public void arrange() {
        if (isEmpty(tasks)) {
            System.out.println("List of tasks is empty!");
        } else {
            Task[] cloneTask = new Task[tasks.length];

            System.arraycopy(tasks, 0, cloneTask, 0, cloneTask.length);

            System.out.print("Increasing or decreasing date? (i/d) ");
            String option = sc.nextLine().trim();

            switch (option) {

                case "i" -> {
                    for (int i = 0; i < cloneTask.length; i++) {
                        int min = i;
                        for (int j = i + 1; j < cloneTask.length; j++) {
                            if (Integer.parseInt(cloneTask[min].getCompleteDate().split("-", 3)[2]) > Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[2])) {
                                min = j;
                            }
                            if (Integer.parseInt(cloneTask[min].getCompleteDate().split("-", 3)[2]) == Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[2])) {
                                if (Integer.parseInt(cloneTask[min].getCompleteDate().split("-", 3)[1]) > Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[1])) {
                                    min = j;
                                }
                                if (Integer.parseInt(cloneTask[min].getCompleteDate().split("-", 3)[1]) == Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[1])) {
                                    if (Integer.parseInt(cloneTask[min].getCompleteDate().split("-", 3)[0]) > Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[0])) {
                                        min = j;
                                    }
                                }
                            }
                        }
                        Task temp = cloneTask[min];
                        cloneTask[min] = cloneTask[i];
                        cloneTask[i] = temp;
                    }

                    displayArrangeTask(cloneTask);
                }

                case "d" -> {
                    for (int i = 0; i < cloneTask.length; i++) {
                        int max = i;
                        for (int j = i + 1; j < cloneTask.length; j++) {
                            if (Integer.parseInt(cloneTask[max].getCompleteDate().split("-", 3)[2]) < Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[2])) {
                                max = j;
                            }
                            if (Integer.parseInt(cloneTask[max].getCompleteDate().split("-", 3)[2]) == Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[2])) {
                                if (Integer.parseInt(cloneTask[max].getCompleteDate().split("-", 3)[1]) < Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[1])) {
                                    max = j;
                                }
                                if (Integer.parseInt(cloneTask[max].getCompleteDate().split("-", 3)[1]) == Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[1])) {
                                    if (Integer.parseInt(cloneTask[max].getCompleteDate().split("-", 3)[0]) < Integer.parseInt(cloneTask[j].getCompleteDate().split("-", 3)[0])) {
                                        max = j;
                                    }
                                }
                            }
                        }
                        Task temp = cloneTask[max];
                        cloneTask[max] = cloneTask[i];
                        cloneTask[i] = temp;
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

                if (id <= tasks.length && !tasks[index].getTitle().equals("Deleted") && tasks[index].getAssignTo().equals(name)) {
                    if (tasks[index].getStatus().equals("Incomplete")) {
                        System.out.println("Task [" + id + "] (" + tasks[index].getCompleteDate() + ")"
                                + ": " + tasks[index].getTitle()
                                + " - " + tasks[index].getText());

                        System.out.print("Have you completed the task " + id + "? (y/n) ");
                        String option = sc.nextLine().trim();

                        if (option.equals("y")) {
                            tasks[index].setStatus("Completed");
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

    public boolean isEmpty(Task[] tasks) {
        int checkEmpty = 0;
        int checkDeleted = 0;

        while (checkEmpty < tasks.length) {
            if (tasks[checkEmpty] == null) {
                break;
            }
            checkEmpty++;
        }

        int i = 0;

        while (i < tasks.length) {
            if (checkEmpty != 0) {
                if (tasks[i].getTitle().equals("Deleted")) {
                    checkDeleted++;
                }
            }
            i++;
        }

        return checkEmpty == 0 || checkDeleted == tasks.length;
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
        if (tasks.length == 0) {
            return 0;
        }
        return tasks.length;
    }
}
