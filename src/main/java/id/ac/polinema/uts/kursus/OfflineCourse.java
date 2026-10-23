package id.ac.polinema.uts.kursus;

public class OfflineCourse extends Course {
    private String room;

    public OfflineCourse(String title, int year, String room) {
        super(title, year);

        if (room == null || room.isBlank()) {
            throw new IllegalArgumentException(
                    "Ruangan tidak boleh kosong"
            );
        }

        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public int durationWeeks() {
        return 10;
    }

    @Override
    public String describe() {
        return super.describe() + " [room " + room + "]";
    }
}