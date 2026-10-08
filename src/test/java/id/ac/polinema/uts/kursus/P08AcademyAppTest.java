package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Menjalankan AcademyApp dengan input skrip (bukan keyboard sungguhan),
 * lalu memeriksa teks yang dicetak.
 */
@Timeout(10)
class P08AcademyAppTest {

    private static Object academy() throws Throwable {
        return R.create("Academy", "Polinema Academy");
    }

    private static String run(Object academy, String... lines) throws Throwable {
        String input = String.join("\n", lines) + "\n";
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Object app = R.create("AcademyApp", academy, new Scanner(input), out);
        R.call(app, "run");
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static void assertHas(String output, String expected) {
        assertTrue(output.contains(expected),
                "Output harus memuat \"" + expected + "\". Output sebenarnya:\n" + output);
    }

    @Test
    @DisplayName("Menu tampil lengkap dan pilihan 0 mencetak 'Bye'")
    void menuAndExit() throws Throwable {
        String out = run(academy(), "0");
        assertHas(out, "=== Polinema Academy ===");
        for (String line : new String[] {"1. List courses", "2. Add online course", "3. Add offline course",
                "4. Add workshop", "5. Register student", "6. Enroll student", "7. Drop course",
                "8. Search courses", "9. List students", "0. Exit"}) {
            assertHas(out, line);
        }
        assertHas(out, "Select: ");
        assertHas(out, "Bye");
    }

    @Test
    @DisplayName("Pilihan yang tidak dikenal mencetak 'Invalid option' lalu menu muncul lagi")
    void invalidOption() throws Throwable {
        String out = run(academy(), "x", "42", "0");
        assertHas(out, "Invalid option");
        assertEquals(3, out.split("0\\. Exit", -1).length - 1, "Menu harus dicetak ulang di setiap putaran");
    }

    @Test
    @DisplayName("Daftar kosong mencetak 'No courses' dan 'No students'")
    void emptyLists() throws Throwable {
        String out = run(academy(), "1", "9", "0");
        assertHas(out, "No courses");
        assertHas(out, "No students");
    }

    @Test
    @DisplayName("Tambah kursus online lalu tampilkan: describe, status, dan durasi")
    void addOnlineCourseAndList() throws Throwable {
        Object academy = academy();
        String out = run(academy, "2", "OOP-101", "Java Basics", "2023", "Dewi Lestari", "Software Engineering",
                "1", "0");
        assertHas(out, "Course added");
        assertHas(out, "Java Basics (2023) by Dewi Lestari | Open | 6 weeks");
        assertEquals(1, ((Object[]) R.call(academy, "getCourses")).length);
    }

    @Test
    @DisplayName("Tambah kursus online tanpa pengajar: nama pengajar dikosongkan")
    void addOnlineCourseWithoutInstructor() throws Throwable {
        String out = run(academy(), "2", "WEB-201", "Web Design", "2018", "", "1", "0");
        assertHas(out, "Course added");
        assertHas(out, "Web Design (2018) by TBA | Open | 6 weeks");
    }

    @Test
    @DisplayName("Tambah kursus offline dan workshop")
    void addOfflineAndWorkshop() throws Throwable {
        String out = run(academy(), "3", "Database Systems", "2021", "R-204",
                "4", "Git Bootcamp", "2024", "30", "1", "0");
        assertHas(out, "Database Systems (2021) [room R-204] | Open | 10 weeks");
        assertHas(out, "Git Bootcamp (2024) | Open | 2 weeks");
    }

    @Test
    @DisplayName("Tahun bukan angka, atau di bawah 2015, mencetak 'Invalid number' dan kursus tidak ditambah")
    void invalidYear() throws Throwable {
        Object academy = academy();
        String out = run(academy,
                "2", "OOP-101", "Java Basics", "abc",
                "3", "Database Systems", "-5", "R-204",
                "4", "Git Bootcamp", "2014", "30",
                "4", "Git Bootcamp", "2024", "0",
                "0");
        assertHas(out, "Invalid number");
        assertEquals(0, ((Object[]) R.call(academy, "getCourses")).length);
    }

    @Test
    @DisplayName("Kode, judul, atau ruangan kosong mencetak 'Invalid input'")
    void blankInput() throws Throwable {
        Object academy = academy();
        String out = run(academy, "2", "", "Java Basics", "2023", "3", "Database Systems", "2021", "", "0");
        assertHas(out, "Invalid input");
        assertEquals(0, ((Object[]) R.call(academy, "getCourses")).length);
    }

    @Test
    @DisplayName("Daftar mahasiswa baru, ID kembar ditolak")
    void registerStudent() throws Throwable {
        String out = run(academy(), "5", "S101", "Rina", "5", "S101", "Toni", "9", "0");
        assertHas(out, "Student registered");
        assertHas(out, "Student ID already exists");
        assertHas(out, "S101 - Rina (0 of 3 courses)");
    }

    @Test
    @DisplayName("Alur lengkap: tambah mahasiswa dan kursus, daftar, lihat status, batalkan")
    void enrollAndDrop() throws Throwable {
        Object academy = academy();
        String out = run(academy,
                "5", "S101", "Rina",
                "3", "Database Systems", "2021", "R-204",
                "6", "S101", "Database Systems",
                "1", "9",
                "7", "S101", "Database Systems",
                "1", "9",
                "0");
        assertHas(out, "Enrollment successful");
        assertHas(out, "Database Systems (2021) [room R-204] | Full | 10 weeks");
        assertHas(out, "S101 - Rina (1 of 3 courses)");
        assertHas(out, "Drop successful");
        assertHas(out, "Database Systems (2021) [room R-204] | Open | 10 weeks");
        assertHas(out, "S101 - Rina (0 of 3 courses)");
    }

    @Test
    @DisplayName("Daftar dan batal yang tidak valid mencetak 'Enrollment failed' dan 'Drop failed'")
    void failures() throws Throwable {
        String out = run(academy(), "6", "X", "Y", "7", "X", "Y", "0");
        assertHas(out, "Enrollment failed");
        assertHas(out, "Drop failed");
    }

    @Test
    @DisplayName("Cari kursus: tampilkan yang cocok, atau 'No courses found'")
    void search() throws Throwable {
        Object academy = academy();
        R.call(academy, "addCourse", new OnlineCourse("C1", "Java Basics", 2023));
        R.call(academy, "addCourse", R.create("OfflineCourse", "Database Systems", 2021, "R-204"));
        String out = run(academy, "8", "java", "8", "zzz", "0");
        assertHas(out, "Java Basics (2023) by TBA");
        assertHas(out, "No courses found");
        assertTrue(!out.contains("Database Systems (2021)"), "Database Systems tidak cocok dengan kata kunci 'java'");
    }

    @Test
    @DisplayName("Main mencetak sapaan, lalu menjalankan AcademyApp dengan data contoh")
    void mainStartsTheApp() {
        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes(StandardCharsets.UTF_8)));
        try {
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
        String out = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(out.startsWith("Welcome to Polinema Academy"),
                "Baris pertama output Main harus sapaan. Output sebenarnya:\n" + out);
        assertHas(out, "=== Polinema Academy ===");
        assertHas(out, "Bye");
        assertTrue(!out.contains("No courses"),
                "Main harus mengisi data contoh sebelum menjalankan AcademyApp");
    }
}
