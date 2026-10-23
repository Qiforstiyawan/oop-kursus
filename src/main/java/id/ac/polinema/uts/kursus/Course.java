package id.ac.polinema.uts.kursus;

public class Course {
    protected String title;
    protected int year;
    private boolean available;
    private int extraWeeks;

    public Course(String title, int year) {
        setTitle(title);
        setYear(year);
        available = true;
        extraWeeks = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Judul kursus tidak boleh kosong"
            );
        }

        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 2015) {
            throw new IllegalArgumentException(
                    "Tahun harus minimal 2015"
            );
        }

        this.year = year;
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

    public int durationWeeks() {
        return 2;
    }

    public String describe() {
        return title + " (" + year + ")";
    }

    @Override
    public String toString() {
        return describe();
    }

    public void extendEnrollment() {
        extendEnrollment(4);
    }

    public void extendEnrollment(int weeks) {
        if (weeks <= 0) {
            throw new IllegalArgumentException(
                    "Jumlah minggu harus lebih dari 0"
            );
        }

        extraWeeks += weeks;
    }

    public int getTotalWeeks() {
        return durationWeeks() + extraWeeks;
    }
}