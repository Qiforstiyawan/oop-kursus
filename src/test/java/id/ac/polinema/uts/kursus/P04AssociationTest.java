package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Asosiasi: OnlineCourse mengenal Instructor, tetapi Instructor hidup sendiri. */
class P04AssociationTest {

    @Test
    @DisplayName("Kursus baru belum punya pengajar (null)")
    void newCourseHasNoInstructor() throws Throwable {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        assertNull(R.call(c, "getInstructor"));
    }

    @Test
    @DisplayName("getInstructor mengembalikan objek Instructor yang sama (asosiasi)")
    void sameInstructorObject() throws Throwable {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        Instructor dewi = new Instructor("Dewi Lestari", "Software Engineering");
        R.call(c, "setInstructor", dewi);
        assertSame(dewi, R.call(c, "getInstructor"));
    }

    @Test
    @DisplayName("Dua kursus bisa berbagi satu Instructor")
    void coursesShareInstructor() throws Throwable {
        OnlineCourse a = new OnlineCourse("C1", "Java Basics", 2023);
        OnlineCourse b = new OnlineCourse("C2", "Java Advanced", 2022);
        Instructor dewi = new Instructor("Dewi Lestari", "Software Engineering");
        R.call(a, "setInstructor", dewi);
        R.call(b, "setInstructor", dewi);
        assertSame(R.call(a, "getInstructor"), R.call(b, "getInstructor"));
        assertEquals("Dewi Lestari", dewi.getName());
    }

    @Test
    @DisplayName("setInstructor(null) melepas pengajar")
    void clearInstructor() throws Throwable {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        R.call(c, "setInstructor", new Instructor("Dewi Lestari", "Software Engineering"));
        R.call(c, "setInstructor", (Object) null);
        assertNull(R.call(c, "getInstructor"));
    }
}
