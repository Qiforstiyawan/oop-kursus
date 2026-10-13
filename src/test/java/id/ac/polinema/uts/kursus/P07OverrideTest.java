package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Overriding: subclass mengganti perilaku method yang diwarisi. */
class P07OverrideTest {

    private static Object course() throws Throwable {
        return R.create("Course", "Generic", 2020);
    }

    private static Object offline() throws Throwable {
        return R.create("OfflineCourse", "Database Systems", 2021, "R-204");
    }

    private static Object workshop() throws Throwable {
        return R.create("Workshop", "Git Bootcamp", 2024, 30);
    }

    @Test
    @DisplayName("durationWeeks: Course 2, OnlineCourse 6, OfflineCourse 10, Workshop mewarisi 2")
    void durationWeeks() throws Throwable {
        assertEquals(2, R.call(course(), "durationWeeks"));
        assertEquals(6, R.call(new OnlineCourse("C1", "Java Basics", 2023), "durationWeeks"));
        assertEquals(10, R.call(offline(), "durationWeeks"));
        assertEquals(2, R.call(workshop(), "durationWeeks"));
    }

    @Test
    @DisplayName("OnlineCourse dan OfflineCourse meng-override durationWeeks; Workshop tidak")
    void whoOverrides() {
        assertTrue(R.declaresMethod(OnlineCourse.class, "durationWeeks"), "OnlineCourse harus override durationWeeks");
        assertTrue(R.declaresMethod(R.cls("OfflineCourse"), "durationWeeks"), "OfflineCourse harus override durationWeeks");
        assertFalse(R.declaresMethod(R.cls("Workshop"), "durationWeeks"),
                "Workshop tidak perlu override durationWeeks, ia memakai versi Course");
    }

    @Test
    @DisplayName("describe di Course: 'title (year)'")
    void courseDescribe() throws Throwable {
        assertEquals("Generic (2020)", R.call(course(), "describe"));
    }

    @Test
    @DisplayName("describe di OnlineCourse memakai super.describe() lalu menambah ' by pengajar'")
    void onlineCourseDescribe() throws Throwable {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        assertEquals("Java Basics (2023) by TBA", R.call(c, "describe"));
        R.call(c, "setInstructor", new Instructor("Dewi Lestari", "Software Engineering"));
        assertEquals("Java Basics (2023) by Dewi Lestari", R.call(c, "describe"));
    }

    @Test
    @DisplayName("describe di OfflineCourse menambah ruangan")
    void offlineDescribe() throws Throwable {
        assertEquals("Database Systems (2021) [room R-204]", R.call(offline(), "describe"));
    }

    @Test
    @DisplayName("describe di Workshop memakai versi Course")
    void workshopDescribe() throws Throwable {
        assertEquals("Git Bootcamp (2024)", R.call(workshop(), "describe"));
    }

    @Test
    @DisplayName("toString mengembalikan describe() untuk semua jenis kursus")
    void toStringUsesDescribe() throws Throwable {
        Object[] courses = {course(), new OnlineCourse("C1", "Java Basics", 2023), offline(), workshop()};
        for (Object c : courses) {
            assertEquals(R.call(c, "describe"), c.toString(),
                    "toString harus mengembalikan describe() pada " + c.getClass().getSimpleName());
        }
    }
}
