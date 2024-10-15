package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Permet de maintenir une base de données propre entre les tests
public class CourseServicesImplTest {

    @Autowired
    private ICourseServices courseServices;

    @Autowired
    private ICourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        // Supprimer tous les cours existants avant chaque test
        courseRepository.deleteAll();
    }

    @Test
    public void testAddCourse() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(2);

        // Act
        Course savedCourse = courseServices.addCourse(course);

        // Assert
        assertNotNull(savedCourse.getNumCourse()); // Vérifie que l'ID a été généré
        assertEquals(course.getLevel(), savedCourse.getLevel());
        assertEquals(course.getTypeCourse(), savedCourse.getTypeCourse());
        assertEquals(course.getSupport(), savedCourse.getSupport());
        assertEquals(course.getPrice(), savedCourse.getPrice());
        assertEquals(course.getTimeSlot(), savedCourse.getTimeSlot());
    }

    @Test
    public void testRetrieveAllCourses() {
        // Arrange
        Course course1 = new Course();
        course1.setLevel(1);
        course1.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course1.setSupport(Support.SKI);
        course1.setPrice(100.0f);
        course1.setTimeSlot(2);

        Course course2 = new Course();
        course2.setLevel(2);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
        course2.setSupport(Support.SNOWBOARD);
        course2.setPrice(150.0f);
        course2.setTimeSlot(3);

        courseServices.addCourse(course1);
        courseServices.addCourse(course2);

        // Act
        List<Course> courses = courseServices.retrieveAllCourses();

        // Assert
        assertEquals(2, courses.size()); // Vérifie qu'il y a 2 cours
        assertTrue(courses.contains(course1));
        assertTrue(courses.contains(course2));
    }

    @Test
    public void testRetrieveCourse() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(2);

        Course savedCourse = courseServices.addCourse(course);
        Long courseId = savedCourse.getNumCourse();

        // Act
        Course retrievedCourse = courseServices.retrieveCourse(courseId);

        // Assert
        assertNotNull(retrievedCourse);
        assertEquals(course.getLevel(), retrievedCourse.getLevel());
        assertEquals(course.getTypeCourse(), retrievedCourse.getTypeCourse());
        assertEquals(course.getSupport(), retrievedCourse.getSupport());
        assertEquals(course.getPrice(), retrievedCourse.getPrice());
        assertEquals(course.getTimeSlot(), retrievedCourse.getTimeSlot());
    }

    @Test
    public void testUpdateCourse() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(2);

        Course savedCourse = courseServices.addCourse(course);
        Long courseId = savedCourse.getNumCourse();

        // Act
        savedCourse.setLevel(2);
        savedCourse.setTypeCourse(TypeCourse.INDIVIDUAL);
        savedCourse.setSupport(Support.SNOWBOARD);
        savedCourse.setPrice(200.0f);
        savedCourse.setTimeSlot(4);
        Course updatedCourse = courseServices.updateCourse(savedCourse);

        // Assert
        assertEquals(2, updatedCourse.getLevel());
        assertEquals(TypeCourse.INDIVIDUAL, updatedCourse.getTypeCourse());
        assertEquals(Support.SNOWBOARD, updatedCourse.getSupport());
        assertEquals(200.0f, updatedCourse.getPrice());
        assertEquals(4, updatedCourse.getTimeSlot());
        assertEquals(courseId, updatedCourse.getNumCourse());
    }

    @Test
    public void testDeleteCourse() {
        // Arrange
        Course course = new Course();
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(2);

        Course savedCourse = courseServices.addCourse(course);
        Long courseId = savedCourse.getNumCourse();

        // Act
        courseServices.deleteCourse(courseId);

        // Assert
        Course deletedCourse = courseServices.retrieveCourse(courseId);
        assertNull(deletedCourse); // Vérifie que le cours a été supprimé
    }

    @Test
    public void testRetrieveCourseNotFound() {
        // Act
        Course retrievedCourse = courseServices.retrieveCourse(999L); // Un ID qui n'existe pas

        // Assert
        assertNull(retrievedCourse); // Vérifie qu'aucun cours n'est trouvé
    }
}
