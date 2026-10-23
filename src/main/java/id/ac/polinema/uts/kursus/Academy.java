
package id.ac.polinema.uts.kursus;

public class Academy {
    private String name;
    private Course[] courses;
    private int courseCount;
    private Student[] students;
    private int studentCount;

    public Academy(String name) {
        this.name = name;
        courses = new Course[20];
        courseCount = 0;
        students = new Student[10];
        studentCount = 0;
    }

    public String getName() {
        return name;
    }

    public boolean addCourse(Course course) {
        if (course == null || courseCount >= courses.length) {
            return false;
        }

        courses[courseCount] = course;
        courseCount++;
        return true;
    }

    public boolean addStudent(Student student) {
        if (student == null || studentCount >= students.length) {
            return false;
        }

        if (findStudent(student.getStudentId()) != null) {
            return false;
        }

        students[studentCount] = student;
        studentCount++;
        return true;
    }

    public Course[] getCourses() {
        Course[] result = new Course[courseCount];

        for (int i = 0; i < courseCount; i++) {
            result[i] = courses[i];
        }

        return result;
    }

    public Student[] getStudents() {
        Student[] result = new Student[studentCount];

        for (int i = 0; i < studentCount; i++) {
            result[i] = students[i];
        }

        return result;
    }

    public Course findCourseByTitle(String title) {
        if (title == null) {
            return null;
        }

        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getTitle().equalsIgnoreCase(title)) {
                return courses[i];
            }
        }

        return null;
    }

    public Student findStudent(String studentId) {
        if (studentId == null) {
            return null;
        }

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId().equals(studentId)) {
                return students[i];
            }
        }

        return null;
    }

    public Course[] searchByKeyword(String keyword) {
        if (keyword == null) {
            return new Course[0];
        }

        Course[] result = new Course[courseCount];
        int count = 0;

        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getTitle().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                result[count] = courses[i];
                count++;
            }
        }

        Course[] found = new Course[count];

        for (int i = 0; i < count; i++) {
            found[i] = result[i];
        }

        return found;
    }

    public boolean enroll(String studentId, String title) {
        Student student = findStudent(studentId);
        Course course = findCourseByTitle(title);

        if (student == null || course == null) {
            return false;
        }

        if (!student.canEnroll() || !course.isAvailable()) {
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

    public boolean drop(String studentId, String title) {
        Student student = findStudent(studentId);
        Course course = findCourseByTitle(title);

        if (student == null || course == null) {
            return false;
        }

        if (course.isAvailable() || student.getCourseCount() <= 0) {
            return false;
        }

        if (!student.removeEnrollment()) {
            return false;
        }

        course.releaseSeat();
        return true;
    }
}
