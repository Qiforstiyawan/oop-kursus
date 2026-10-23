package id.ac.polinema.uts.kursus;

public class Registrar {
    public boolean enroll(Student student, OnlineCourse course) {
        if (student == null || course == null
                || !student.canEnroll() || !course.isAvailable()) {
            return false;
        }

        if (!course.takeSeat()) {
            return false;
        }

        if (!student.addEnrollment()) {
            course.releaseSeat();
            return false;
        }

        return true;
    }

    public boolean drop(Student student, OnlineCourse course) {
        if (student == null || course == null
                || course.isAvailable() || student.getCourseCount() <= 0) {
            return false;
        }

        course.releaseSeat();
        student.removeEnrollment();
        return true;
    }
}