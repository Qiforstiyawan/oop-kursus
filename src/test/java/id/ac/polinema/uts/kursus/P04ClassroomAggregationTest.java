package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Agregasi: Classroom menyimpan OnlineCourse, tetapi OnlineCourse ada di luar Classroom juga. */
class P04ClassroomAggregationTest {

    private static Object classroom() throws Throwable {
        return R.create("Classroom", "R1");
    }

    @Test
    @DisplayName("Classroom ada, semua field-nya private, dan menyimpan kode kelas")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("Classroom"));
        Object room = classroom();
        assertEquals("R1", R.call(room, "getCode"));
        assertEquals(0, R.call(room, "getCourseCount"));
    }

    @Test
    @DisplayName("addCourse menambah kursus dan getCourseCount ikut naik")
    void addCourse() throws Throwable {
        Object room = classroom();
        assertEquals(true, R.call(room, "addCourse", new OnlineCourse("C1", "Java Basics", 2023)));
        assertEquals(true, R.call(room, "addCourse", new OnlineCourse("C2", "Web Design", 2018)));
        assertEquals(2, R.call(room, "getCourseCount"));
    }

    @Test
    @DisplayName("Kapasitas kelas 5 kursus; kursus ke-6 ditolak")
    void capacity() throws Throwable {
        Object room = classroom();
        for (int i = 1; i <= 5; i++) {
            assertEquals(true, R.call(room, "addCourse", new OnlineCourse("C" + i, "Course " + i, 2020)));
        }
        assertEquals(false, R.call(room, "addCourse", new OnlineCourse("C6", "Course 6", 2020)));
        assertEquals(5, R.call(room, "getCourseCount"));
    }

    @Test
    @DisplayName("addCourse(null) ditolak")
    void rejectsNull() throws Throwable {
        Object room = classroom();
        assertEquals(false, R.call(room, "addCourse", (Object) null));
        assertEquals(0, R.call(room, "getCourseCount"));
    }

    @Test
    @DisplayName("findByCode mengembalikan objek OnlineCourse yang sama (agregasi), atau null")
    void findByCode() throws Throwable {
        Object room = classroom();
        OnlineCourse java = new OnlineCourse("C111", "Java Basics", 2023);
        OnlineCourse web = new OnlineCourse("C222", "Web Design", 2018);
        R.call(room, "addCourse", java);
        R.call(room, "addCourse", web);
        assertSame(web, R.call(room, "findByCode", "C222"));
        assertSame(java, R.call(room, "findByCode", "C111"));
        assertNull(R.call(room, "findByCode", "C999"));
    }

    @Test
    @DisplayName("countAvailable mengikuti status kursus yang berubah lewat referensi luar")
    void countAvailable() throws Throwable {
        Object room = classroom();
        OnlineCourse a = new OnlineCourse("C1", "Java Basics", 2023);
        OnlineCourse b = new OnlineCourse("C2", "Web Design", 2018);
        R.call(room, "addCourse", a);
        R.call(room, "addCourse", b);
        assertEquals(2, R.call(room, "countAvailable"));
        assertTrue(a.takeSeat());
        assertEquals(1, R.call(room, "countAvailable"));
        a.releaseSeat();
        assertEquals(2, R.call(room, "countAvailable"));
        assertTrue(b.isAvailable());
    }
}
