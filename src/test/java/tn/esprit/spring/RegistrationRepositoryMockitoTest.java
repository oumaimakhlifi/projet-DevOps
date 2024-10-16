package tn.esprit.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.RegistrationServicesImpl;

@TestMethodOrder(OrderAnnotation.class)
public class RegistrationRepositoryMockitoTest {

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationService; // Assume there's a service that uses the repository

    private Course testCourse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testCourse = new Course();
        testCourse.setLevel(1);
        testCourse.setSupport(Support.SKI);
        testCourse.setPrice(150.0f);
        testCourse.setTimeSlot(1);
    }

    /*@Test
    public void addRegistrationsTest() {
        Registration registration1 = new Registration();
        registration1.setCourse(testCourse);
        registration1.setNumWeek(1);

        Registration registration2 = new Registration();
        registration2.setCourse(testCourse);
        registration2.setNumWeek(1);

        // Mocking the save method to return the registration objects
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration1).thenReturn(registration2);

        // Call the save method on the repository
        Registration savedReg1 = registrationRepository.save(registration1);
        Registration savedReg2 = registrationRepository.save(registration2);

        // Assertions to check that the saved registrations are not null
        Assertions.assertNotNull(savedReg1.getNumRegistration(), "Registration 1 should have a valid ID.");
        Assertions.assertNotNull(savedReg2.getNumRegistration(), "Registration 2 should have a valid ID.");
    }*/

    @Test
    public void countByCourseAndNumWeekTest() {
        // Mock the expected count for the specific course and week
        when(registrationRepository.countByCourseAndNumWeek(testCourse, 1)).thenReturn(2L);

        long count = registrationRepository.countByCourseAndNumWeek(testCourse, 1);
        Assertions.assertEquals(2, count, "The count should be 2 for the given course and week.");
    }

    @Test
    public void testCountWithNoRegistrations() {
        Course anotherCourse = new Course();
        anotherCourse.setLevel(2);
        anotherCourse.setSupport(Support.SNOWBOARD);
        anotherCourse.setPrice(200.0f);
        anotherCourse.setTimeSlot(2);

        when(courseRepository.save(any(Course.class))).thenReturn(anotherCourse);
        when(registrationRepository.countByCourseAndNumWeek(anotherCourse, 1)).thenReturn(0L);

        long count = registrationRepository.countByCourseAndNumWeek(anotherCourse, 1);
        Assertions.assertEquals(0, count, "The count should be 0 for a course with no registrations.");
    }

    @Test
    public void countDistinctByNumWeekAndSkierAndCourseTest() {
        Long numSkier = 1L; // Example skier ID
        Long numCourse = testCourse.getNumCourse(); // Get the course ID

        // Mocking for the case where two distinct registrations exist
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, numSkier, numCourse)).thenReturn(2L);
        long count = registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, numSkier, numCourse);
        Assertions.assertEquals(2, count, "The count should be 2 for the given skier and course in the same week.");

        // Mocking for the case with a non-existent skier
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 999L, numCourse)).thenReturn(0L);
        long zeroCount = registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 999L, numCourse);
        Assertions.assertEquals(0, zeroCount, "The count should be 0 for a non-existent skier.");
    }
}
