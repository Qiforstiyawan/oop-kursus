package id.ac.polinema.uts.kursus;

/**
 * Bagian 3: mahasiswa peserta kursus.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README
 * (semua field harus private).
 *
 * Kerangka field (lengkapi dari diagram), letakkan di atas constructor:
 * private ____ studentId;
 * private ____ name;
 * private int courseCount; // nilai awal 0
 */

public class Student {
    private String studentId;
    private String name;
    private int courseCount;
    private StudentCard card;

    public Student(String studentId, String name) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException(
                    "ID mahasiswa tidak boleh kosong"
            );
        }

        this.studentId = studentId;
        setName(name);
        this.courseCount = 0;
        this.card = new StudentCard("SC-" + studentId);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Nama tidak boleh kosong"
            );
        }

        this.name = name;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public StudentCard getCard() {
        return card;
    }

    public boolean canEnroll() {
        return courseCount < 3;
    }

    public boolean addEnrollment() {
        if (!canEnroll()) {
            return false;
        }

        courseCount++;
        return true;
    }

    public boolean removeEnrollment() {
        if (courseCount <= 0) {
            return false;
        }

        courseCount--;
        return true;
    }
}