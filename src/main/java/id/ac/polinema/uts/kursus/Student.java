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

    /**
     * Membuat mahasiswa yang belum mengambil kursus.
     * studentId dan name tidak boleh null atau kosong: lempar
     * IllegalArgumentException jika tidak valid.
     */
    public Student(String studentId, String name) {
        // TODO Bantuan: validasi dulu sebelum mengisi field. Aturan nama sama dengan setName.
        // Bagian 4: kartu mahasiswa juga dibuat di sini (lihat diagram Bagian 4).
        //
        // Kerangka:
        //   if (studentId == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.studentId = studentId;
        //   setName(____);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getStudentId() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getName() {
        // TODO Bantuan: sama seperti getStudentId.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Nama tidak boleh null atau kosong: lempar IllegalArgumentException dan biarkan nama lama. */
    public void setName(String name) {
        // TODO Bantuan: periksa dulu, baru isi field.
        //
        // Kerangka:
        //   if (name == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.name = name;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getCourseCount() {
        // TODO Bantuan: kembalikan jumlah kursus yang sedang diambil.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True jika mahasiswa masih boleh mengambil kursus (batasnya 3 kursus). */
    public boolean canEnroll() {
        // TODO Bantuan: apakah perlu field baru, atau cukup dihitung dari data yang sudah ada?
        //
        // Kerangka:
        //   return courseCount ____ 3;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Menambah jumlah kursus. Jika sudah di batas, tidak berubah dan hasilnya false. */
    public boolean addEnrollment() {
        // TODO Bantuan: periksa batas dulu. Adakah method lain di kelas ini yang sudah
        // menjawab pertanyaan "boleh atau tidak"?
        //
        // Kerangka:
        //   if (!____) {
        //       return false;
        //   }
        //   courseCount++;
        //   return true;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Mengurangi jumlah kursus. Jika sudah tidak ada kursus, tidak berubah dan hasilnya false. */
    public boolean removeEnrollment() {
        // TODO Bantuan: batas bawah jumlah kursus adalah berapa?
        //
        // Kerangka:
        //   if (courseCount == ____) {
        //       return false;
        //   }
        //   courseCount--;
        //   return true;
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
