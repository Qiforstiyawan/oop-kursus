package id.ac.polinema.uts.kursus;

import java.io.PrintStream;
import java.util.Scanner;

public class AcademyApp {
    private Academy academy;
    private Scanner in;
    private PrintStream out;

    public AcademyApp(Academy academy, Scanner in, PrintStream out) {
        this.academy = academy;
        this.in = in;
        this.out = out;
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = readLine("Select: ");

            if (choice.isEmpty()) {
                break;
            }

            switch (choice) {
                case "1":
                    showCourses(academy.getCourses(), "No courses");
                    break;
                case "2":
                    addOnlineCourse();
                    break;
                case "3":
                    addOfflineCourse();
                    break;
                case "4":
                    addWorkshop();
                    break;
                case "5":
                    registerStudent();
                    break;
                case "6":
                    enrollStudent();
                    break;
                case "7":
                    dropCourse();
                    break;
                case "8":
                    searchCourses();
                    break;
                case "9":
                    showStudents();
                    break;
                case "0":
                    out.println("Bye");
                    running = false;
                    break;
                default:
                    out.println("Invalid option");
                    break;
            }
        }
    }

    private void showMenu() {
        out.println("=== " + academy.getName() + " ===");
        out.println("1. List courses");
        out.println("2. Add online course");
        out.println("3. Add offline course");
        out.println("4. Add workshop");
        out.println("5. Register student");
        out.println("6. Enroll student");
        out.println("7. Drop course");
        out.println("8. Search courses");
        out.println("9. List students");
        out.println("0. Exit");
    }

    private String readLine(String prompt) {
        out.print(prompt);

        if (in.hasNextLine()) {
            return in.nextLine();
        }

        return "";
    }

    private int readInt(String prompt) {
        String value = readLine(prompt);

        try {
            int number = Integer.parseInt(value.trim());

            if (number <= 0) {
                return -1;
            }

            return number;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void showCourses(Course[] courses, String emptyMessage) {
        if (courses.length == 0) {
            out.println(emptyMessage);
            return;
        }

        for (int i = 0; i < courses.length; i++) {
            String status;

            if (courses[i].isAvailable()) {
                status = "Open";
            } else {
                status = "Full";
            }

            out.println(courses[i].describe() + " | " + status + " | "
                    + courses[i].durationWeeks() + " weeks");
        }
    }

    private void addOnlineCourse() {
        String code = readLine("Code: ");
        String title = readLine("Title: ");
        int year = readInt("Year: ");

        if (year < 2015) {
            out.println("Invalid number");
            return;
        }

        if (code.trim().isEmpty() || title.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        String instructorName =
                readLine("Instructor name (leave blank for none): ");

        Instructor instructor = null;

        if (!instructorName.trim().isEmpty()) {
            String expertise = readLine("Instructor expertise: ");
            instructor = new Instructor(instructorName, expertise);
        }

        OnlineCourse course =
                new OnlineCourse(code, title, year, instructor);

        if (academy.addCourse(course)) {
            out.println("Course added");
        } else {
            out.println("Academy is full");
        }
    }

    private void addOfflineCourse() {
        String title = readLine("Title: ");
        int year = readInt("Year: ");
        String room = readLine("Room: ");

        if (year < 2015) {
            out.println("Invalid number");
            return;
        }

        if (title.trim().isEmpty() || room.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        OfflineCourse course = new OfflineCourse(title, year, room);

        if (academy.addCourse(course)) {
            out.println("Course added");
        } else {
            out.println("Academy is full");
        }
    }

    private void addWorkshop() {
        String title = readLine("Title: ");
        int year = readInt("Year: ");
        int seats = readInt("Seats: ");

        if (year < 2015 || seats <= 0) {
            out.println("Invalid number");
            return;
        }

        if (title.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        Workshop workshop = new Workshop(title, year, seats);

        if (academy.addCourse(workshop)) {
            out.println("Course added");
        } else {
            out.println("Academy is full");
        }
    }

    private void registerStudent() {
        String id = readLine("Student ID: ");
        String name = readLine("Name: ");

        if (id.trim().isEmpty() || name.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        Student student = new Student(id, name);

        if (academy.addStudent(student)) {
            out.println("Student registered");
        } else {
            out.println("Student ID already exists");
        }
    }

    private void enrollStudent() {
        String id = readLine("Student ID: ");
        String title = readLine("Course title: ");

        if (academy.enroll(id, title)) {
            out.println("Enrollment successful");
        } else {
            out.println("Enrollment failed");
        }
    }

    private void dropCourse() {
        String id = readLine("Student ID: ");
        String title = readLine("Course title: ");

        if (academy.drop(id, title)) {
            out.println("Drop successful");
        } else {
            out.println("Drop failed");
        }
    }

    private void searchCourses() {
        String keyword = readLine("Keyword: ");
        showCourses(academy.searchByKeyword(keyword),
                "No courses found");
    }

    private void showStudents() {
        Student[] students = academy.getStudents();

        if (students.length == 0) {
            out.println("No students");
            return;
        }

        for (int i = 0; i < students.length; i++) {
            out.println(students[i].getStudentId() + " - "
                    + students[i].getName() + " ("
                    + students[i].getCourseCount() + " of 3 courses)");
        }
    }
}