package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;

import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {

    private final SessionFactory sessionFactory;

    public CourseService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createCourse(Course course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
    }

    /**
     * Return course if exists, also handle commit, rollback, and exceptions.
     */
    @Override
    public Course getCourseById(int courseId) {
        Transaction transaction = null;
        Course course = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            course = session.get(Course.class, courseId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log the exception properly in real-world applications
        }
        return course;
    }

    /**
     * Return all courses from database, also handle commit, rollback, and exceptions.
     */
    @Override
    public List<Course> getAllCourses() {
        Transaction transaction = null;
        List<Course> courses = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            courses = session.createQuery("FROM Course", Course.class).list();
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