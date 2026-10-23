
package id.ac.polinema.uts.kursus;

/**
 * Bagian 2: pengajar kursus.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Di Bagian 2 field boleh public. Di Bagian 3 kamu akan menutupnya.
 */

public class Instructor {
    private String name;
    private String expertise;

    public Instructor(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getInfo() {
        return name + " (" + expertise + ")";
    }
}
