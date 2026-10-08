package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class P03EncapsulationTest {

    @Test
    @DisplayName("Semua field OnlineCourse private")
    void courseFieldsPrivate() {
        R.assertAllFieldsPrivate(OnlineCourse.class);
    }

    @Test
    @DisplayName("Semua field Instructor private")
    void instructorFieldsPrivate() {
        R.assertAllFieldsPrivate(Instructor.class);
    }

    @Test
    @DisplayName("code read-only: OnlineCourse tidak punya setCode, dan status tidak bisa di-set langsung")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(OnlineCourse.class, "setCode"), "code tidak boleh punya setter");
        assertFalse(R.hasPublicMethod(OnlineCourse.class, "setAvailable"),
                "status tersedia hanya boleh berubah lewat takeSeat dan releaseSeat");
    }

    @Test
    @DisplayName("setTitle menolak null, kosong, dan hanya spasi; judul lama tetap")
    void setTitleRejectsBlank() {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        assertThrows(IllegalArgumentException.class, () -> c.setTitle(null));
        assertThrows(IllegalArgumentException.class, () -> c.setTitle(""));
        assertThrows(IllegalArgumentException.class, () -> c.setTitle("   "));
        assertEquals("Java Basics", c.getTitle());
    }

    @Test
    @DisplayName("setTitle menerima judul yang valid")
    void setTitleAcceptsValid() {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        c.setTitle("Java Advanced");
        assertEquals("Java Advanced", c.getTitle());
    }

    @Test
    @DisplayName("setYear menolak tahun di bawah 2015; tahun lama tetap")
    void setYearRejectsBefore2015() {
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        assertThrows(IllegalArgumentException.class, () -> c.setYear(2014));
        assertThrows(IllegalArgumentException.class, () -> c.setYear(0));
        assertThrows(IllegalArgumentException.class, () -> c.setYear(-5));
        assertEquals(2023, c.getYear());
        c.setYear(2015);
        assertEquals(2015, c.getYear());
        c.setYear(2021);
        assertEquals(2021, c.getYear());
    }

    @Test
    @DisplayName("Constructor OnlineCourse memvalidasi code, title, dan year")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new OnlineCourse("", "Java Basics", 2023));
        assertThrows(IllegalArgumentException.class, () -> new OnlineCourse(null, "Java Basics", 2023));
        assertThrows(IllegalArgumentException.class, () -> new OnlineCourse("C1", " ", 2023));
        assertThrows(IllegalArgumentException.class, () -> new OnlineCourse("C1", "Java Basics", 2014));
        assertThrows(IllegalArgumentException.class, () -> new OnlineCourse("C1", "Java Basics", 0));
    }
}
