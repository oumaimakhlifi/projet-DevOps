package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class CourseServicesImplTest {

    @Autowired
    private ICourseServices courseServices;

    @Autowired
    private ICourseRepository courseRepository;

    @Mock
    private ICourseRepository mockCourseRepository;

    @InjectMocks
    private CourseServicesImpl mockCourseServices;

    @BeforeEach
    public void setUp() {
        // Pour les tests avec le vrai dépôt
        courseRepository.deleteAll();
        MockitoAnnotations.openMocks(this); // Pour les tests avec Mockito
    }

    // Tests avec JUnit
    @Test
    public void testAddCourse() {
        Course course = new Course();
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(2);

        Course savedCourse = courseServices.addCourse(course);

        assertNotNull(savedCourse.getNumCourse());
        assertEquals(course.getLevel(), savedCourse.getLevel());
    }

    @Test
    public void testRetrieveAllCourses() {
        Course course1 = new Course();
        course1.setLevel(1);
        course1.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course1.setSupport(Support.SKI);
        course1.setPrice(100.0f);
        course1.setTimeSlot(2);

        courseServices.addCourse(course1);

        List<Course> courses = courseServices.retrieveAllCourses();

        assertEquals(1, courses.size());
        assertTrue(courses.contains(course1));
    }

    // Tests avec Mockito
    @Test
    public void testAddCourseMockito() {
        Course course = new Course();
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(2);

        when(mockCourseRepository.save(any(Course.class))).thenReturn(course);

        Course savedCourse = mockCourseServices.addCourse(course);

        assertNotNull(savedCourse);
        assertEquals(course.getLevel(), savedCourse.getLevel());
        verify(mockCourseRepository, times(1)).save(course);
    }

    @Test
    public void testRetrieveCourseMockito() {
        Course course = new Course();
        course.setNumCourse(1L);
        course.setLevel(1);
        course.setTypeCourse(TypeCourse.COLLECTIVE_CHILDREN);
        course.setSupport(Support.SKI);
        course.setPrice(100.0f);
        course.setTimeSlot(2);

        when(mockCourseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course retrievedCourse = mockCourseServices.retrieveCourse(1L);

        assertNotNull(retrievedCourse);
        assertEquals(course.getLevel(), retrievedCourse.getLevel());
        verify(mockCourseRepository, times(1)).findById(1L);
    }
}
