package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Academy berisi logika saja: tidak boleh mencetak apa pun. */
@Timeout(10)
class P08AcademyTest {

    private static Object academy() throws Throwable {
        return R.create("Academy", "Polinema Academy");
    }

    private static Object offline(String title) throws Throwable {
        return R.create("OfflineCourse", title, 2021, "R-204");
    }

    private static Object workshop(String title) throws Throwable {
        return R.create("Workshop", title, 2024, 30);
    }

    @Test
    @DisplayName("Academy menyimpan nama dan semua field-nya private")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("Academy"));
        assertEquals("Polinema Academy", R.call(academy(), "getName"));
    }

    @Test
    @DisplayName("addCourse menerima OnlineCourse, OfflineCourse, dan Workshop dalam satu array")
    void mixedCourses() throws Throwable {
        Object academy = academy();
        OnlineCourse online = new OnlineCourse("C1", "Java Basics", 2023);
        Object offline = offline("Database Systems");
        Object workshop = workshop("Git Bootcamp");
        assertEquals(true, R.call(academy, "addCourse", online));
        assertEquals(true, R.call(academy, "addCourse", offline));
        assertEquals(true, R.call(academy, "addCourse", workshop));
        Object[] courses = (Object[]) R.call(academy, "getCourses");
        assertEquals(3, courses.length, "getCourses hanya mengembalikan kursus yang terisi");
        assertSame(online, courses[0]);
        assertSame(offline, courses[1]);
        assertSame(workshop, courses[2]);
    }

    @Test
    @DisplayName("addCourse menolak null dan menolak jika sudah penuh (20 kursus)")
    void courseCapacity() throws Throwable {
        Object academy = academy();
        assertEquals(false, R.call(academy, "addCourse", (Object) null));
        for (int i = 1; i <= 20; i++) {
            assertEquals(true, R.call(academy, "addCourse", workshop("Workshop " + i)));
        }
        assertEquals(false, R.call(academy, "addCourse", workshop("One too many")));
        assertEquals(20, ((Object[]) R.call(academy, "getCourses")).length);
    }

    @Test
    @DisplayName("addStudent menolak id kembar dan null; findStudent mengembalikan objek yang sama")
    void students() throws Throwable {
        Object academy = academy();
        Student rina = new Student("S101", "Rina");
        assertEquals(true, R.call(academy, "addStudent", rina));
        assertEquals(false, R.call(academy, "addStudent", new Student("S101", "Orang Lain")));
        assertEquals(false, R.call(academy, "addStudent", (Object) null));
        assertEquals(1, ((Object[]) R.call(academy, "getStudents")).length);
        assertSame(rina, R.call(academy, "findStudent", "S101"));
        assertNull(R.call(academy, "findStudent", "S999"));
    }

    @Test
    @DisplayName("addStudent menolak jika sudah penuh (10 mahasiswa)")
    void studentCapacity() throws Throwable {
        Object academy = academy();
        for (int i = 1; i <= 10; i++) {
            assertEquals(true, R.call(academy, "addStudent", new Student("S" + i, "Student " + i)));
        }
        assertEquals(false, R.call(academy, "addStudent", new Student("S11", "Student 11")));
    }

    @Test
    @DisplayName("findCourseByTitle tidak peduli huruf besar-kecil; null jika tidak ada")
    void findCourse() throws Throwable {
        Object academy = academy();
        OnlineCourse course = new OnlineCourse("C1", "Java Basics", 2023);
        R.call(academy, "addCourse", course);
        assertSame(course, R.call(academy, "findCourseByTitle", "java basics"));
        assertSame(course, R.call(academy, "findCourseByTitle", "JAVA BASICS"));
        assertNull(R.call(academy, "findCourseByTitle", "Unknown"));
    }

    @Test
    @DisplayName("searchByKeyword mengembalikan semua kursus yang judulnya memuat kata kunci")
    void search() throws Throwable {
        Object academy = academy();
        OnlineCourse basics = new OnlineCourse("C1", "Java Basics", 2023);
        OnlineCourse advanced = new OnlineCourse("C2", "Advanced Java", 2019);
        Object offline = offline("Database Systems");
        R.call(academy, "addCourse", basics);
        R.call(academy, "addCourse", offline);
        R.call(academy, "addCourse", advanced);
        Object[] found = (Object[]) R.call(academy, "searchByKeyword", "java");
        assertEquals(2, found.length);
        assertSame(basics, found[0]);
        assertSame(advanced, found[1]);
        Object[] none = (Object[]) R.call(academy, "searchByKeyword", "zzz");
        assertEquals(0, none.length, "Tanpa hasil, kembalikan array kosong, bukan null");
    }

    @Test
    @DisplayName("enroll berhasil: kursi terisi dan jumlah kursus Student naik")
    void enrollSuccess() throws Throwable {
        Object academy = academy();
        Student rina = new Student("S101", "Rina");
        OnlineCourse course = new OnlineCourse("C1", "Java Basics", 2023);
        R.call(academy, "addStudent", rina);
        R.call(academy, "addCourse", course);
        assertEquals(true, R.call(academy, "enroll", "S101", "Java Basics"));
        assertEquals(false, course.isAvailable());
        assertEquals(1, rina.getCourseCount());
    }

    @Test
    @DisplayName("enroll gagal untuk Student atau judul yang tidak ada, dan kursus yang kursinya sudah terisi")
    void enrollFailures() throws Throwable {
        Object academy = academy();
        R.call(academy, "addStudent", new Student("S101", "Rina"));
        R.call(academy, "addStudent", new Student("S102", "Toni"));
        R.call(academy, "addCourse", new OnlineCourse("C1", "Java Basics", 2023));
        assertEquals(false, R.call(academy, "enroll", "S999", "Java Basics"));
        assertEquals(false, R.call(academy, "enroll", "S101", "Unknown"));
        assertEquals(true, R.call(academy, "enroll", "S101", "Java Basics"));
        assertEquals(false, R.call(academy, "enroll", "S102", "Java Basics"));
    }

    @Test
    @DisplayName("enroll gagal jika Student sudah mengambil 3 kursus")
    void enrollLimit() throws Throwable {
        Object academy = academy();
        Student rina = new Student("S101", "Rina");
        R.call(academy, "addStudent", rina);
        for (int i = 1; i <= 4; i++) {
            R.call(academy, "addCourse", workshop("Workshop " + i));
        }
        assertEquals(true, R.call(academy, "enroll", "S101", "Workshop 1"));
        assertEquals(true, R.call(academy, "enroll", "S101", "Workshop 2"));
        assertEquals(true, R.call(academy, "enroll", "S101", "Workshop 3"));
        assertEquals(false, R.call(academy, "enroll", "S101", "Workshop 4"));
        assertEquals(3, rina.getCourseCount());
        Object fourth = R.call(academy, "findCourseByTitle", "Workshop 4");
        assertEquals(true, R.call(fourth, "isAvailable"));
    }

    @Test
    @DisplayName("drop berhasil lalu gagal jika kursus tidak sedang diambil atau data tidak ada")
    void drop() throws Throwable {
        Object academy = academy();
        Student rina = new Student("S101", "Rina");
        Object offline = offline("Database Systems");
        R.call(academy, "addStudent", rina);
        R.call(academy, "addCourse", offline);
        assertEquals(false, R.call(academy, "drop", "S101", "Database Systems"));
        R.call(academy, "enroll", "S101", "Database Systems");
        assertEquals(true, R.call(academy, "drop", "S101", "Database Systems"));
        assertEquals(true, R.call(offline, "isAvailable"));
        assertEquals(0, rina.getCourseCount());
        assertEquals(false, R.call(academy, "drop", "S999", "Database Systems"));
        assertEquals(false, R.call(academy, "drop", "S101", "Unknown"));
    }

    @Test
    @DisplayName("Academy tidak boleh mencetak ke layar (pisahkan logika dan tampilan)")
    void academyDoesNotPrint() throws Throwable {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            Object academy = academy();
            R.call(academy, "addStudent", new Student("S101", "Rina"));
            R.call(academy, "addCourse", new OnlineCourse("C1", "Java Basics", 2023));
            R.call(academy, "enroll", "S101", "Java Basics");
            R.call(academy, "drop", "S101", "Java Basics");
            R.call(academy, "searchByKeyword", "java");
            R.call(academy, "getCourses");
        } finally {
            System.setOut(original);
        }
        assertTrue(buffer.toString(StandardCharsets.UTF_8).isEmpty(),
                "Academy hanya berisi logika. Semua System.out.println ada di AcademyApp");
    }
}
