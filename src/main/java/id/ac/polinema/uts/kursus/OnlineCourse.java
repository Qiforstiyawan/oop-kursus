package id.ac.polinema.uts.kursus;

/**
 * Bagian 2 dan 3: kursus online.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Method untuk bagian berikutnya (setInstructor, getInstructor, dan seterusnya) kamu
 * tambahkan sendiri sesuai diagram di bagian tersebut.
 *
 * Kerangka field (lengkapi tipe dan nama dari diagram), letakkan di atas constructor:
 *   public String code;
 *   public ____ title;
 *   public ____ year;
 *   public ____ available;
 */

/**
 * Bagian 2 dan 3: kursus online.
 */

public class OnlineCourse extends Course {
    private String code;
    private Instructor instructor;

    public OnlineCourse(String code, String title, int year) {
        super(title, year);

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException(
                    "Kode kursus tidak boleh kosong"
            );
        }

        this.code = code;
    }

    public OnlineCourse(String code, String title, int year, Instructor instructor) {
        this(code, title, year);
        this.instructor = instructor;
    }

    public String getCode() {
        return code;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public int durationWeeks() {
        return 6;
    }

    @Override
    public String describe() {
        return super.describe() + " by "
                + (instructor == null ? "TBA" : instructor.getName());
    }
}