package id.ac.polinema.uts.kursus;

public class Workshop extends Course {
    private int seats;

    public Workshop(String title, int year, int seats) {
        super(title, year);

        if (seats <= 0) {
            throw new IllegalArgumentException(
                    "Jumlah kursi harus lebih dari 0"
            );
        }

        this.seats = seats;
    }

    public Workshop(String title, int year) {
        this(title, year, 20);
    }

    public int getSeats() {
        return seats;
    }
}