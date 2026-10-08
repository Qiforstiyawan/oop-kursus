package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Overloading: nama method atau constructor sama, parameter berbeda. */
class P07OverloadTest {

    @Test
    @DisplayName("Course punya dua method extendEnrollment dengan parameter berbeda")
    void twoExtendEnrollment() {
        int count = 0;
        for (Method m : R.cls("Course").getDeclaredMethods()) {
            if (m.getName().equals("extendEnrollment")) {
                count++;
            }
        }
        assertEquals(2, count, "Harus ada extendEnrollment() dan extendEnrollment(int weeks)");
    }

    @Test
    @DisplayName("extendEnrollment() menambah 4 minggu")
    void extendDefault() throws Throwable {
        Object offline = R.create("OfflineCourse", "Database Systems", 2021, "R-204");
        assertEquals(10, R.call(offline, "getTotalWeeks"));
        R.call(offline, "extendEnrollment");
        assertEquals(14, R.call(offline, "getTotalWeeks"));
    }

    @Test
    @DisplayName("extendEnrollment(int) menambah sebanyak minggu yang diberikan dan bisa berulang")
    void extendWeeks() throws Throwable {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        assertEquals(6, R.call(c, "getTotalWeeks"));
        R.call(c, "extendEnrollment", 3);
        R.call(c, "extendEnrollment", 2);
        assertEquals(11, R.call(c, "getTotalWeeks"));
    }

    @Test
    @DisplayName("extendEnrollment(int) menolak 0 dan negatif; total tidak berubah")
    void extendRejectsNonPositive() throws Throwable {
        Object w = R.create("Workshop", "Git Bootcamp", 2024, 30);
        assertThrows(IllegalArgumentException.class, () -> R.call(w, "extendEnrollment", 0));
        assertThrows(IllegalArgumentException.class, () -> R.call(w, "extendEnrollment", -4));
        assertEquals(2, R.call(w, "getTotalWeeks"));
    }

    @Test
    @DisplayName("Constructor OnlineCourse(code, title, year, instructor) langsung mengisi pengajar")
    void onlineCourseConstructorWithInstructor() throws Throwable {
        Instructor dewi = new Instructor("Dewi Lestari", "Software Engineering");
        Object c = R.create("OnlineCourse", "C1", "Java Basics", 2023, dewi);
        assertSame(dewi, R.call(c, "getInstructor"));
        assertEquals("Java Basics", R.call(c, "getTitle"));
        assertEquals("C1", R.call(c, "getCode"));
        assertEquals(true, R.call(c, "isAvailable"));
    }

    @Test
    @DisplayName("Constructor Workshop(title, year) memberi 20 kursi; versi 3 parameter tetap jalan")
    void workshopConstructors() throws Throwable {
        Object a = R.create("Workshop", "Git Bootcamp", 2024);
        assertEquals(20, R.call(a, "getSeats"));
        Object b = R.create("Workshop", "Git Bootcamp", 2024, 45);
        assertEquals(45, R.call(b, "getSeats"));
    }
}
