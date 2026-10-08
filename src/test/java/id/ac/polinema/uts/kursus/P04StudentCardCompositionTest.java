package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Komposisi: StudentCard dibuat di dalam Student dan tidak datang dari luar. */
class P04StudentCardCompositionTest {

    @Test
    @DisplayName("StudentCard ada dan field-nya private")
    void cardClass() {
        R.assertAllFieldsPrivate(R.cls("StudentCard"));
    }

    @Test
    @DisplayName("Student punya kartu bernomor 'SC-' + studentId")
    void studentHasCard() throws Throwable {
        Student s = new Student("S101", "Rina");
        Object card = R.call(s, "getCard");
        assertNotNull(card, "Student harus membuat StudentCard sendiri di constructor");
        assertEquals("SC-S101", R.call(card, "getNumber"));
    }

    @Test
    @DisplayName("getCard selalu mengembalikan kartu yang sama untuk satu Student")
    void sameCardEveryTime() throws Throwable {
        Student s = new Student("S101", "Rina");
        assertSame(R.call(s, "getCard"), R.call(s, "getCard"));
    }

    @Test
    @DisplayName("Dua Student punya dua kartu berbeda")
    void differentStudentsDifferentCards() throws Throwable {
        Student a = new Student("S101", "Rina");
        Student b = new Student("S102", "Toni");
        assertNotSame(R.call(a, "getCard"), R.call(b, "getCard"));
        assertEquals("SC-S102", R.call(R.call(b, "getCard"), "getNumber"));
    }

    @Test
    @DisplayName("Constructor Student tidak menerima StudentCard dari luar (komposisi)")
    void cardIsNotPassedIn() {
        Class<?> card = R.cls("StudentCard");
        for (Constructor<?> k : Student.class.getConstructors()) {
            for (Class<?> p : k.getParameterTypes()) {
                org.junit.jupiter.api.Assertions.assertNotEquals(card, p,
                        "Student harus MEMBUAT StudentCard sendiri, bukan menerimanya dari parameter");
            }
        }
    }
}
