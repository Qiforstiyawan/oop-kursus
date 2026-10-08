package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Dependensi: Registrar hanya memakai Student dan OnlineCourse lewat parameter. */
class P04RegistrarDependencyTest {

    private static Object registrar() throws Throwable {
        return R.create("Registrar");
    }

    @Test
    @DisplayName("Registrar tidak menyimpan apa pun sebagai field (dependensi, bukan asosiasi)")
    void noFields() {
        Class<?> c = R.cls("Registrar");
        assertEquals(0, c.getDeclaredFields().length,
                "Registrar hanya BERGANTUNG pada Student dan OnlineCourse lewat parameter, jangan simpan sebagai field");
    }

    @Test
    @DisplayName("enroll berhasil: kursi terisi dan jumlah kursus Student naik")
    void enrollSuccess() throws Throwable {
        Object r = registrar();
        Student s = new Student("S101", "Rina");
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        assertEquals(true, R.call(r, "enroll", s, c));
        assertFalse(c.isAvailable());
        assertEquals(1, s.getCourseCount());
    }

    @Test
    @DisplayName("enroll gagal jika kursi sudah terisi; jumlah kursus tidak berubah")
    void enrollSeatTaken() throws Throwable {
        Object r = registrar();
        Student a = new Student("S101", "Rina");
        Student b = new Student("S102", "Toni");
        OnlineCourse course = new OnlineCourse("C1", "Java Basics", 2023);
        R.call(r, "enroll", a, course);
        assertEquals(false, R.call(r, "enroll", b, course));
        assertEquals(0, b.getCourseCount());
    }

    @Test
    @DisplayName("enroll gagal jika Student sudah mengambil 3 kursus; kursus tetap tersedia")
    void enrollStudentAtLimit() throws Throwable {
        Object r = registrar();
        Student s = new Student("S101", "Rina");
        for (int i = 1; i <= 3; i++) {
            R.call(r, "enroll", s, new OnlineCourse("C" + i, "Course " + i, 2020));
        }
        OnlineCourse fourth = new OnlineCourse("C4", "Course 4", 2020);
        assertEquals(false, R.call(r, "enroll", s, fourth));
        assertTrue(fourth.isAvailable());
        assertEquals(3, s.getCourseCount());
    }

    @Test
    @DisplayName("drop berhasil: kursus tersedia lagi dan jumlah kursus turun")
    void dropSuccess() throws Throwable {
        Object r = registrar();
        Student s = new Student("S101", "Rina");
        OnlineCourse c = new OnlineCourse("C1", "Java Basics", 2023);
        R.call(r, "enroll", s, c);
        assertEquals(true, R.call(r, "drop", s, c));
        assertTrue(c.isAvailable());
        assertEquals(0, s.getCourseCount());
    }

    @Test
    @DisplayName("drop gagal untuk kursus yang tidak sedang diambil")
    void dropNotEnrolled() throws Throwable {
        Object r = registrar();
        Student s = new Student("S101", "Rina");
        assertEquals(false, R.call(r, "drop", s, new OnlineCourse("C1", "Java Basics", 2023)));
        assertEquals(0, s.getCourseCount());
    }
}
