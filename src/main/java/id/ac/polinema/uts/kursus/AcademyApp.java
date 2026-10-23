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
            String choice = readLine("Pilih: ");

            if (choice.isEmpty()) {
                break;
            }

            switch (choice) {
                case "1":
                    showCourses(academy.getCourses(), "Belum ada kursus");
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
                    out.println("Keluar");
                    running = false;
                    break;
                default:
                    out.println("Pilihan tidak valid");
                    break;
            }
        }
    }

    private void showMenu() {
        out.println("=== " + academy.getName() + " ===");
        out.println("1. Daftar kursus");
        out.println("2. Tambah kursus online");
        out.println("3. Tambah kursus offline");
        out.println("4. Tambah workshop");
        out.println("5. Daftarkan mahasiswa");
        out.println("6. Ikut kursus");
        out.println("7. Batalkan pendaftaran kursus");
        out.println("8. Cari kursus");
        out.println("9. Daftar mahasiswa");
        out.println("0. Keluar");
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
                status = "Tersedia";
            } else {
                status = "Penuh";
            }

            out.println(courses[i].describe() + " | " + status + " | "
                    + courses[i].durationWeeks() + " minggu");
        }
    }

    private void addOnlineCourse() {
        String code = readLine("Kode: ");
        String title = readLine("Judul: ");
        int year = readInt("Tahun: ");

        if (year < 2015) {
            out.println("Angka tidak valid");
            return;
        }

        if (code.trim().isEmpty() || title.trim().isEmpty()) {
            out.println("Masukan tidak valid");
            return;
        }

        String instructorName =
                readLine("Nama pengajar (kosongkan jika tidak ada): ");

        Instructor instructor = null;

        if (!instructorName.trim().isEmpty()) {
            String expertise = readLine("Bidang keahlian pengajar: ");
            instructor = new Instructor(instructorName, expertise);
        }

        OnlineCourse course =
                new OnlineCourse(code, title, year, instructor);

        if (academy.addCourse(course)) {
            out.println("Kursus berhasil ditambahkan");
        } else {
            out.println("Kapasitas akademi penuh");
        }
    }

    private void addOfflineCourse() {
        String title = readLine("Judul: ");
        int year = readInt("Tahun: ");
        String room = readLine("Ruangan: ");

        if (year < 2015) {
            out.println("Angka tidak valid");
            return;
        }

        if (title.trim().isEmpty() || room.trim().isEmpty()) {
            out.println("Masukan tidak valid");
            return;
        }

        OfflineCourse course = new OfflineCourse(title, year, room);

        if (academy.addCourse(course)) {
            out.println("Kursus berhasil ditambahkan");
        } else {
            out.println("Kapasitas akademi penuh");
        }
    }

    private void addWorkshop() {
        String title = readLine("Judul: ");
        int year = readInt("Tahun: ");
        int seats = readInt("Jumlah kursi: ");

        if (year < 2015 || seats <= 0) {
            out.println("Angka tidak valid");
            return;
        }

        if (title.trim().isEmpty()) {
            out.println("Masukan tidak valid");
            return;
        }

        Workshop workshop = new Workshop(title, year, seats);

        if (academy.addCourse(workshop)) {
            out.println("Kursus berhasil ditambahkan");
        } else {
            out.println("Kapasitas akademi penuh");
        }
    }

    private void registerStudent() {
        String id = readLine("ID mahasiswa: ");
        String name = readLine("Nama: ");

        if (id.trim().isEmpty() || name.trim().isEmpty()) {
            out.println("Masukan tidak valid");
            return;
        }

        Student student = new Student(id, name);

        if (academy.addStudent(student)) {
            out.println("Mahasiswa berhasil didaftarkan");
        } else {
            out.println("ID mahasiswa sudah terdaftar atau kapasitas penuh");
        }
    }

    private void enrollStudent() {
        String id = readLine("ID mahasiswa: ");
        String title = readLine("Judul kursus: ");

        if (academy.enroll(id, title)) {
            out.println("Pendaftaran kursus berhasil");
        } else {
            out.println("Pendaftaran kursus gagal");
        }
    }

    private void dropCourse() {
        String id = readLine("ID mahasiswa: ");
        String title = readLine("Judul kursus: ");

        if (academy.drop(id, title)) {
            out.println("Pembatalan pendaftaran berhasil");
        } else {
            out.println("Pembatalan pendaftaran gagal");
        }
    }

    private void searchCourses() {
        String keyword = readLine("Kata kunci: ");
        showCourses(academy.searchByKeyword(keyword),
                "Kursus tidak ditemukan");
    }

    private void showStudents() {
        Student[] students = academy.getStudents();

        if (students.length == 0) {
            out.println("Belum ada mahasiswa");
            return;
        }

        for (int i = 0; i < students.length; i++) {
            out.println(students[i].getStudentId() + " - "
                    + students[i].getName() + " ("
                    + students[i].getCourseCount() + " dari 3 kursus)");
        }
    }
}