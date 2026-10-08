package id.ac.polinema.uts.kursus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class P02InstructorTest {

    @Test
    @DisplayName("Constructor menyimpan nama dan keahlian, getter mengembalikannya")
    void constructorAndGetters() {
        Instructor i = new Instructor("Dewi Lestari", "Software Engineering");
        assertEquals("Dewi Lestari", i.getName());
        assertEquals("Software Engineering", i.getExpertise());
    }

    @Test
    @DisplayName("getInfo mengembalikan 'nama (keahlian)'")
    void getInfo() {
        Instructor i = new Instructor("Hendra Wijaya", "Databases");
        assertEquals("Hendra Wijaya (Databases)", i.getInfo());
    }

    @Test
    @DisplayName("Dua objek Instructor punya data sendiri-sendiri")
    void objectsAreIndependent() {
        Instructor a = new Instructor("Dewi Lestari", "Software Engineering");
        Instructor b = new Instructor("Hendra Wijaya", "Databases");
        assertNotEquals(a.getName(), b.getName());
        assertEquals("Dewi Lestari", a.getName());
        assertEquals("Databases", b.getExpertise());
    }
}
