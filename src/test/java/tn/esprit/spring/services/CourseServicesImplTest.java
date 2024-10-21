package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServicesImplTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseService;

    // List to simulate the database
    private List<Course> courseList;

    // Test objects
    private Course course1;
    private Course course2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        courseList = new ArrayList<>();

        // Create Course objects for testing
        course1 = new Course();
        course1.setNumCourse(1L);
        course1.setLevel(1);
        course1.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course1.setSupport(Support.SKI);
        course1.setPrice(100.0f);
        course1.setTimeSlot(2);

        course2 = new Course();
        course2.setNumCourse(2L);
        course2.setLevel(2);
        course2.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);
        course2.setSupport(Support.SNOWBOARD);
        course2.setPrice(150.0f);
        course2.setTimeSlot(3);
    }

    // Test for adding a course (Create)
    @Test
    public void testAddCourse() {
        // Simuler le comportement de save avec Mockito
        when(courseRepository.save(any(Course.class))).thenAnswer(invocation -> {
            Course savedCourse = invocation.getArgument(0);
            savedCourse.setNumCourse(1L); // Simule un ID auto-généré
            return savedCourse;
        });

        // Appel de la méthode à tester
        Course addedCourse = courseService.addCourse(course1);

        // Assertions pour vérifier que le cours a été ajouté correctement
        assertNotNull(addedCourse, "Le cours ajouté ne doit pas être null");
        assertEquals(course1.getLevel(), addedCourse.getLevel(), "Le niveau du cours ajouté est incorrect");
        assertEquals(1L, addedCourse.getNumCourse(), "L'ID du cours ajouté doit être 1");
        verify(courseRepository, times(1)).save(course1); // Vérifie que save a été appelé une fois
    }

    // Test for retrieving a course (Read)
    @Test
    public void testRetrieveCourse() {
        Long courseId = 1L;

        // Configuration du mock pour simuler la présence du cours
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course1));

        // Appel de la méthode à tester
        Course retrievedCourse = courseService.retrieveCourse(courseId);

        // Assertions pour vérifier que le cours a été récupéré correctement
        assertNotNull(retrievedCourse, "Le cours récupéré ne doit pas être null");
        assertEquals(courseId, retrievedCourse.getNumCourse(), "L'ID du cours récupéré est incorrect");
        assertEquals(course1.getLevel(), retrievedCourse.getLevel(), "Le niveau du cours récupéré est incorrect");
        verify(courseRepository, times(1)).findById(courseId); // Vérifie que findById a été appelé
    }



    // Test for updating a course
    @Test
    void testUpdateCourse() {
        // Configuration des comportements des mocks
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));
        when(courseRepository.save(any(Course.class))).thenReturn(course1);

        // Appel de la méthode à tester
        Course updatedCourse = courseService.updateCourse(course1);

        // Assertions pour vérifier que le résultat est correct
        assertNotNull(updatedCourse, "Le cours mis à jour ne doit pas être null");
        assertEquals(course1.getLevel(), updatedCourse.getLevel(), "Le niveau du cours mis à jour est incorrect");
        verify(courseRepository, times(1)).save(course1); // Vérifier que save a été appelé
    }

    // Test for deleting a course
    @Test
    void testDeleteCourse() {
        Long courseId = 1L;

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course1));

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).delete(course1);
    }

    // Test for retrieving all courses
    @Test
    public void testRetrieveAllCourses() {
        courseList.add(course1);
        when(courseRepository.findAll()).thenReturn(courseList);

        List<Course> allCourses = courseService.retrieveAllCourses();

        assertEquals(1, allCourses.size());
        assertTrue(allCourses.contains(course1), "Le cours devrait être présent dans la liste.");
    }
}
