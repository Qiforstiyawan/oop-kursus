package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Inheritance: OnlineCourse, OfflineCourse, dan Workshop adalah Course (IS-A). */
class P06InheritanceTest {

    private static Object offline() throws Throwable {
        return R.create("OfflineCourse", "Database Systems", 2021, "R-204");
    }

    private static Object workshop() throws Throwable {
        return R.create("Workshop", "Git Bootcamp", 2024, 30);
    }

    @Test
    @DisplayName("OnlineCourse, OfflineCourse, dan Workshop extends Course")
    void superclass() {
        Class<?> course = R.cls("Course");
        assertEquals(course, OnlineCourse.class.getSuperclass(), "OnlineCourse harus extends Course");
        assertEquals(course, R.cls("OfflineCourse").getSuperclass(), "OfflineCourse harus extends Course");
        assertEquals(course, R.cls("Workshop").getSuperclass(), "Workshop harus extends Course");
    }

    @Test
    @DisplayName("Course menyimpan title dan year sebagai protected, dan tidak ada field public")
    void courseFields() {
        Class<?> course = R.cls("Course");
        for (Field f : course.getDeclaredFields()) {
            if (f.isSynthetic()) {
                continue;
            }
            assertFalse(Modifier.isPublic(f.getModifiers()), "Field " + f.getName() + " tidak boleh public");
        }
        assertTrue(Modifier.isProtected(field(course, "title").getModifiers()), "title harus protected");
        assertTrue(Modifier.isProtected(field(course, "year").getModifiers()), "year harus protected");
    }

    private static Field field(Class<?> c, String name) {
        try {
            return c.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return org.junit.jupiter.api.Assertions.fail("Field " + name + " tidak ada di " + c.getSimpleName());
        }
    }

    @Test
    @DisplayName("OnlineCourse tidak menyimpan ulang title, year, dan available (sudah ada di Course)")
    void onlineCourseDoesNotRepeatFields() {
        for (Field f : OnlineCourse.class.getDeclaredFields()) {
            String n = f.getName();
            assertFalse(n.equals("title") || n.equals("year") || n.equals("available"),
                    "Field " + n + " sudah ada di Course. Hapus dari OnlineCourse.");
        }
        R.assertAllFieldsPrivate(OnlineCourse.class);
    }

    @Test
    @DisplayName("Method umum dipindah ke Course, bukan ditulis ulang di subclass")
    void methodsMovedUp() {
        for (String name : new String[] {"getTitle", "getYear", "isAvailable", "takeSeat", "releaseSeat"}) {
            assertFalse(R.declaresMethod(OnlineCourse.class, name), "OnlineCourse tidak perlu mendeklarasikan " + name);
            assertFalse(R.declaresMethod(R.cls("OfflineCourse"), name), "OfflineCourse tidak perlu mendeklarasikan " + name);
            assertTrue(R.declaresMethod(R.cls("Course"), name), "Course harus punya " + name);
        }
    }

    @Test
    @DisplayName("Course(title, year) menyimpan data dan memvalidasinya")
    void courseConstructor() throws Throwable {
        Object course = R.create("Course", "Generic", 2020);
        assertEquals("Generic", R.call(course, "getTitle"));
        assertEquals(2020, R.call(course, "getYear"));
        assertEquals(true, R.call(course, "isAvailable"));
        assertThrows(IllegalArgumentException.class, () -> R.create("Course", " ", 2020));
        assertThrows(IllegalArgumentException.class, () -> R.create("Course", "Generic", 2014));
    }

    @Test
    @DisplayName("OfflineCourse menyimpan ruangan dan memakai getter turunan dari Course")
    void offlineKeepsRoom() throws Throwable {
        Object o = offline();
        assertEquals("Database Systems", R.call(o, "getTitle"));
        assertEquals(2021, R.call(o, "getYear"));
        assertEquals("R-204", R.call(o, "getRoom"));
        R.assertAllFieldsPrivate(R.cls("OfflineCourse"));
    }

    @Test
    @DisplayName("Workshop menyimpan jumlah kursi dan memakai getter turunan")
    void workshopKeepsSeats() throws Throwable {
        Object w = workshop();
        assertEquals("Git Bootcamp", R.call(w, "getTitle"));
        assertEquals(30, R.call(w, "getSeats"));
        R.assertAllFieldsPrivate(R.cls("Workshop"));
    }

    @Test
    @DisplayName("takeSeat dan releaseSeat diwarisi oleh semua subclass")
    void inheritedBehaviour() throws Throwable {
        Object[] courses = {new OnlineCourse("C1", "Java Basics", 2023), offline(), workshop()};
        for (Object course : courses) {
            assertEquals(true, R.call(course, "isAvailable"));
            assertEquals(true, R.call(course, "takeSeat"));
            assertEquals(false, R.call(course, "takeSeat"));
            R.call(course, "releaseSeat");
            assertEquals(true, R.call(course, "isAvailable"));
        }
    }

    @Test
    @DisplayName("Validasi setter ikut diwarisi: setTitle kosong ditolak di OfflineCourse")
    void validationInherited() throws Throwable {
        Object o = offline();
        assertThrows(IllegalArgumentException.class, () -> R.call(o, "setTitle", ""));
        assertThrows(IllegalArgumentException.class, () -> R.call(o, "setYear", 2014));
        assertEquals("Database Systems", R.call(o, "getTitle"));
        assertEquals(2021, R.call(o, "getYear"));
    }

    @Test
    @DisplayName("Setiap subclass adalah turunan Course (extends)")
    void isInstance() throws Throwable {
        Class<?> course = R.cls("Course");
        assertTrue(course.isInstance(new OnlineCourse("C1", "Java Basics", 2023)));
        assertTrue(course.isInstance(offline()));
        assertTrue(course.isInstance(workshop()));
    }
}
