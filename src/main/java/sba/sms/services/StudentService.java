package sba.sms.services;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    private final SessionFactory sessionFactory;

    public StudentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createStudent(Student student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
    }

    @Override
    public List<Student> getAllStudents() {
        Transaction transaction = null;
        List<Student> students = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            students = session.createQuery("FROM Student", Student.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
        return students;
    }

    @Override
    public Student getStudentByEmail(String email) {
        Transaction transaction = null;
        Student student = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            student = session.get(Student.class, email);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Transaction transaction = null;
        boolean isValid = false;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Student student = session.createQuery("FROM Student s WHERE s.email = :email AND s.password = :password", Student.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
            isValid = student != null;
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
        return isValid;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);
            if (student != null && course != null && !student.getCourses().contains(course)) {
                student.getCourses().add(course);
                session.merge(student);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Transaction transaction = null;
        List<Course> courses = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "SELECT c.* FROM Course c JOIN student_courses sc ON c.id = sc.courses_id WHERE sc.student_email = :email";
            NativeQuery<Course> query = session.createNativeQuery(sql, Course.class);
            query.setParameter("email", email);
            courses = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
        return courses;
    }
}
