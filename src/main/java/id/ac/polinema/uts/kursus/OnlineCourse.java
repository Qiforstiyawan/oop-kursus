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


public class OnlineCourse {
    private String code;
    private String title;
    private int year;
    private boolean available;
    private Instructor instructor;

    public OnlineCourse(String code, String title, int year) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be empty");
        }

        this.code = code;
        setTitle(title);
        setYear(year);
        this.available = true;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean takeSeat() {
        if (!available) {
            return false;
        }

        available = false;
        return true;
    }

    public void releaseSeat() {
        available = true;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        this.title = title;
    }

    public void setYear(int year) {
        if (year < 2015) {
            throw new IllegalArgumentException("Year must be at least 2015");
        }

        this.year = year;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
