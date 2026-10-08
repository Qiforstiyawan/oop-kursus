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
public class OnlineCourse {

    /**
     * Membuat kursus baru yang kursinya masih kosong (tersedia).
     * Bagian 3: code tidak boleh kosong; title dan year divalidasi seperti setter-nya.
     */
    public OnlineCourse(String code, String title, int year) {
        // TODO Bantuan: isi semua field. Kursus baru: apakah kursinya sudah terisi atau belum?
        // Bagian 3: aturan validasi judul dan tahun sama dengan setter. Bisakah constructor
        // memakai setter-nya?
        //
        // Kerangka (Bagian 2):
        //   this.code = code;
        //   this.title = ____;
        //   this.year = ____;
        //   this.available = ____;
        //
        // Kerangka (Bagian 3): ganti dua baris title dan year dengan pemanggilan setter.
        //   setTitle(____);
        //   setYear(____);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getCode() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        //   return ____;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getTitle() {
        // TODO Bantuan: sama seperti getCode.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getYear() {
        // TODO Bantuan: sama seperti getCode.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True jika kursus masih punya kursi kosong. */
    public boolean isAvailable() {
        // TODO Bantuan: field apa yang menyimpan status kursi?
        //
        // Kerangka:
        //   return ____;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Mengisi kursi kursus. Berhasil (true) jika kursus tersedia. Jika kursi
     * sudah terisi, hasilnya false dan keadaan kursus tidak berubah.
     */
    public boolean takeSeat() {
        // TODO Bantuan: apa yang harus kamu periksa lebih dulu sebelum mengubah status?
        // Method ini punya dua jalur return yang berbeda.
        //
        // Kerangka:
        //   if (____) {
        //       return false;
        //   }
        //   ____ = false;
        //   return true;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Mengosongkan kursi: kursus bisa diambil lagi. */
    public void releaseSeat() {
        // TODO Bantuan: satu baris saja. Status apa yang berubah?
        //
        // Kerangka:
        //   ____ = true;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Bagian 3. Mengubah judul. Judul null, kosong, atau hanya spasi ditolak
     * dengan IllegalArgumentException, dan judul lama tetap.
     */
    public void setTitle(String title) {
        // TODO Bantuan: periksa dulu, baru isi field. Class String punya method untuk
        // memeriksa teks kosong atau hanya spasi. Jangan lupa kemungkinan null.
        //
        // Kerangka:
        //   if (title == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.title = title;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Bagian 3. Mengubah tahun. Tahun di bawah 2015 ditolak dengan
     * IllegalArgumentException, dan tahun lama tetap.
     */
    public void setYear(int year) {
        // TODO Bantuan: pola yang sama dengan setTitle. Apa syarat tahun yang valid?
        //
        // Kerangka:
        //   if (year ____ 2015) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.year = year;
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
