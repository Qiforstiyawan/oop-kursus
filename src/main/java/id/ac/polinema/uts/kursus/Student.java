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
 *   private ____ studentId;
 *   private ____ name;
 *   private int courseCount;   // nilai awal 0
 */
public class Student {
    private String studentId;
    private String name;
    private int courseCount;

    /**
     * Membuat mahasiswa yang belum mengambil kursus.
     * studentId dan name tidak boleh null atau kosong: lempar
     * IllegalArgumentException jika tidak valid.
     */
    public Student(String studentId, String name) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }

        this.studentId = studentId;
        setName(name);
        this.courseCount = 0;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    /** Nama tidak boleh null atau kosong: lempar IllegalArgumentException dan biarkan nama lama. */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.name = name;
    }

    public int getCourseCount() {
        return courseCount;
    }

    /** True jika mahasiswa masih boleh mengambil kursus (batasnya 3 kursus). */
    public boolean canEnroll() {
        return courseCount < 3;
    }

    /** Menambah jumlah kursus. Jika sudah di batas, tidak berubah dan hasilnya false. */
    public boolean addEnrollment() {
        if (!canEnroll()) {
            return false;
        }

        courseCount++;
        return true;
    }

    /** Mengurangi jumlah kursus. Jika sudah tidak ada kursus, tidak berubah dan hasilnya false. */
    public boolean removeEnrollment() {
        if (courseCount == 0) {
            return false;
        }

        courseCount--;
        return true;
    }
}