package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

import java.util.List;

public class StudentService implements StudentI {

    @Override
    public void createStudent(Student student) {
        // creating a student
    }

    @Override
    public List<Student> getAllStudents() {
        // retrieving all students
        return null;
    }

    @Override
    public Student getStudentByEmail(String email) {
        // retrieving a student by email
        return null;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        // validating student credentials
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        // registering a student to a course
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        // retrieving student courses
        return null;
    }
}
