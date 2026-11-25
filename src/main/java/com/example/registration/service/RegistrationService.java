
package com.example.registration.service;

import com.example.registration.model.Student;
import com.example.registration.model.Course;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationService {

    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    private Map<Long, List<Course>> registrations = new HashMap<>();

    public RegistrationService() {

        // Sample student
        students.add(new Student(
                1L,
                "Hala",
                "hala@gmail.com",
                "1234"
        ));

        // Sample courses
        courses.add(new Course(
                1L, "Java Programming",
                "Learn Java basics", 3
        ));

        courses.add(new Course(
                2L, "Database Systems",
                "Learn SQL and databases", 3
        ));

        courses.add(new Course(
                3L, "Software Engineering",
                "Software development concepts", 3
        ));
    }

    // Student Login
    public Student login(String email, String password) {

        for (Student student : students) {

            if (student.getEmail().equals(email)
                    && student.getPassword().equals(password)) {

                return student;
            }
        }

        return null;
    }

    // Get all available courses
    public List<Course> getAllCourses() {
        return courses;
    }

    // Register student in a course
    public boolean register(Student student, Long courseId) {

        Course selectedCourse = null;

        for (Course course : courses) {

            if (course.getId().equals(courseId)) {
                selectedCourse = course;
                break;
            }
        }

        if (selectedCourse == null) {
            return false;
        }

        List<Course> studentCourses =
                registrations.computeIfAbsent(
                        student.getId(),
                        id -> new ArrayList<>()
                );

        if (studentCourses.contains(selectedCourse)) {
            return false;
        }

        studentCourses.add(selectedCourse);

        return true;
    }

    // Get courses registered by a student
    public List<Course> getStudentCourses(Student student) {

        return registrations.getOrDefault(
                student.getId(),
                new ArrayList<>()
        );
    }

    // Add a new student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Add a new course
    public void addCourse(Course course) {
        courses.add(course);
    }
}

