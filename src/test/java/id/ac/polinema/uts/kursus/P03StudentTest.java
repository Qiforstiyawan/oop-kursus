package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P03StudentTest {

    @Test
    @DisplayName("Semua field Student private")
    void fieldsPrivate() {
        R.assertAllFieldsPrivate(Student.class);
    }

    @Test
    @DisplayName("Constructor menyimpan id dan nama; jumlah kursus awal 0")
    void constructor() {
        Student s = new Student("S101", "Rina");
        assertEquals("S101", s.getStudentId());
        assertEquals("Rina", s.getName());
        assertEquals(0, s.getCourseCount());
    }

    @Test
    @DisplayName("Constructor menolak id atau nama null/kosong")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new Student("", "Rina"));
        assertThrows(IllegalArgumentException.class, () -> new Student(null, "Rina"));
        assertThrows(IllegalArgumentException.class, () -> new Student("S101", "  "));
        assertThrows(IllegalArgumentException.class, () -> new Student("S101", null));
    }

    @Test
    @DisplayName("studentId dan courseCount read-only: tidak ada setter")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(Student.class, "setStudentId"));
        assertFalse(R.hasPublicMethod(Student.class, "setCourseCount"));
    }

    @Test
    @DisplayName("setName menolak nama kosong dan menjaga nama lama")
    void setName() {
        Student s = new Student("S101", "Rina");
        assertThrows(IllegalArgumentException.class, () -> s.setName(""));
        assertThrows(IllegalArgumentException.class, () -> s.setName(null));
        assertEquals("Rina", s.getName());
        s.setName("Rina Putri");
        assertEquals("Rina Putri", s.getName());
    }

    @Test
    @DisplayName("addEnrollment sampai batas 3; kursus ke-4 ditolak")
    void addEnrollmentLimit() {
        Student s = new Student("S101", "Rina");
        assertTrue(s.addEnrollment());
        assertTrue(s.addEnrollment());
        assertTrue(s.addEnrollment());
        assertFalse(s.addEnrollment());
        assertEquals(3, s.getCourseCount());
    }

    @Test
    @DisplayName("canEnroll dihitung dari jumlah kursus (derived getter)")
    void canEnrollIsDerived() {
        Student s = new Student("S101", "Rina");
        assertTrue(s.canEnroll());
        s.addEnrollment();
        s.addEnrollment();
        assertTrue(s.canEnroll());
        s.addEnrollment();
        assertFalse(s.canEnroll());
        s.removeEnrollment();
        assertTrue(s.canEnroll());
    }

    @Test
    @DisplayName("removeEnrollment tidak boleh membuat jumlah kursus negatif")
    void removeEnrollmentFloor() {
        Student s = new Student("S101", "Rina");
        assertFalse(s.removeEnrollment());
        assertEquals(0, s.getCourseCount());
        s.addEnrollment();
        assertTrue(s.removeEnrollment());
        assertEquals(0, s.getCourseCount());
    }
}
