package id.ac.polinema.uts.kursus;

public class Classroom {
    private String code;
    private OnlineCourse[] courses;
    private int count;

    public Classroom(String code) {
        this.code = code;
        this.courses = new OnlineCourse[5];
        this.count = 0;
    }

    public String getCode() {
        return code;
    }

    public int getCourseCount() {
        return count;
    }

    public boolean addCourse(OnlineCourse course) {
        if (course == null || count >= courses.length) {
            return false;
        }

        courses[count++] = course;
        return true;
    }

    public OnlineCourse findByCode(String code) {
        for (int i = 0; i < count; i++) {
            if (courses[i].getCode().equals(code)) {
                return courses[i];
            }
        }

        return null;
    }

    public int countAvailable() {
        int available = 0;

        for (int i = 0; i < count; i++) {
            if (courses[i].isAvailable()) {
                available++;
            }
        }

        return available;
    }
}