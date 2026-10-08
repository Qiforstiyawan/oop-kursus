package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P02OnlineCourseTest {

    @Test
    @DisplayName("Constructor menyimpan code, title, dan year")
    void constructorAndGetters() {
        OnlineCourse c = new OnlineCourse("OOP-101", "Java Basics", 2023);
        assertEquals("OOP-101", c.getCode());
        assertEquals("Java Basics", c.getTitle());
        assertEquals(2023, c.getYear());
    }

    @Test
    @DisplayName("Kursus baru langsung punya kursi kosong (tersedia)")
    void newCourseIsAvailable() {
        assertTrue(new OnlineCourse("C1", "Java Basics", 2023).isAvailable());
    }

    @Test
    @DisplayName("takeSeat: berhasil sekali, lalu gagal karena kursi sudah terisi")
    void takeSeat() {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        assertTrue(c.takeSeat());
        assertFalse(c.isAvailable());
        assertFalse(c.takeSeat());
    }

    @Test
    @DisplayName("releaseSeat membuat kursus tersedia lagi")
    void releaseSeat() {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        c.takeSeat();
        c.releaseSeat();
        assertTrue(c.isAvailable());
        assertTrue(c.takeSeat());
    }

    @Test
    @DisplayName("Mengisi kursi satu kursus tidak mengubah kursus lain")
    void objectsAreIndependent() {
        OnlineCourse a = new OnlineCourse("C1", "Java Basics", 2023);
        OnlineCourse b = new OnlineCourse("C2", "Web Design", 2018);
        a.takeSeat();
        assertFalse(a.isAvailable());
        assertTrue(b.isAvailable());
        assertEquals("Web Design", b.getTitle());
    }
}
