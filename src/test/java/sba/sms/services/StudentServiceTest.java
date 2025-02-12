package sba.sms.services;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StudentServiceTest {

    private StudentService studentService;
    private CourseService courseService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        studentService = new StudentService(sessionFactory);
        courseService = new CourseService(sessionFactory);
    }

    @AfterEach
    public void tearDown() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student("john.doe@example.com", "John Doe", "password");
        studentService.createStudent(student);
        Student retrieved = studentService.getStudentByEmail("john.doe@example.com");
        assertNotNull(retrieved);
        assertEquals("john.doe@example.com", retrieved.getEmail());
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = studentService.getAllStudents();
        assertNotNull(students);
        assertTrue(students.size() >= 1);
    }

    @Test
    public void testGetStudentByEmail() {
        Student student = new Student("jane.doe@example.com", "Jane Doe", "password");
        studentService.createStudent(student);
        Student result = studentService.getStudentByEmail("jane.doe@example.com");
        assertNotNull(result);
        assertEquals("jane.doe@example.com", result.getEmail());
    }

    @Test
    public void testValidateStudent() {
        Student student = new Student("john.doe@example.com", "John Doe", "password");
        studentService.createStudent(student);
        boolean isValid = studentService.validateStudent("john.doe@example.com", "password");
        assertTrue(isValid);
    }

    @Test
    public void testRegisterStudentToCourse() {
        Student student = new Student("john.doe@example.com", "John Doe", "password");
        Course course = new Course("Math 101", "Dr. Smith");
        studentService.createStudent(student);
        courseService.createCourse(course);
        studentService.registerStudentToCourse("john.doe@example.com", course.getId());
        List<Course> courses = studentService.getStudentCourses("john.doe@example.com");
        assertTrue(courses.contains(course));
    }

    @Test
    public void testGetStudentCourses() {
        Student student = new Student("john.doe@example.com", "John Doe", "password");
        studentService.createStudent(student);
        List<Course> courses = studentService.getStudentCourses("john.doe@example.com");
        assertNotNull(courses);
        assertTrue(courses.size() >= 0);
    }
}
