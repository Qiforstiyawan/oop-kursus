
package id.ac.polinema.uts.kursus;

/**
 * Bagian 2 dan 3: kursus online.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 */
public class OnlineCourse {
    public String code;
    public String title;
    public int year;
    public boolean available;

    /**
     * Membuat kursus baru yang kursinya masih kosong (tersedia).
     */
    public OnlineCourse(String code, String title, int year) {
        this.code = code;
        this.title = title;
        this.year = year;
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

    /** True jika kursus masih punya kursi kosong. */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Mengisi kursi kursus. Berhasil (true) jika kursus tersedia.
     * Jika sudah terisi, hasilnya false dan keadaan tidak berubah.
     */
    public boolean takeSeat() {
        if (!available) {
            return false;
        }

        available = false;
        return true;
    }

    /** Mengosongkan kursi agar kursus bisa diambil lagi. */
    public void releaseSeat() {
        available = true;
    }
}
