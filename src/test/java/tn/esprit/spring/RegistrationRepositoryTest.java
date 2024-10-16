package tn.esprit.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier; // Ensure you have the Skier entity
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.ISkierRepository; // Inject the skier repository

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Transactional // Automatically rolls back transactions after each test
public class RegistrationRepositoryTest {

    @Autowired
    private IRegistrationRepository registrationRepository;

    @Autowired
    private ICourseRepository courseRepository; // Inject the course repository

    @Autowired
    private ISkierRepository skierRepository; // Inject the skier repository

    private Course testCourse; // Use instance variables
    private Registration registration1;
    private Registration registration2;

    @Test
    @Order(0)
    public void addRegistrationsTest() {
        // Create and save the course first to ensure it's persisted
        testCourse = new Course();
        testCourse.setLevel(1);
        testCourse.setSupport(Support.SKI);
        testCourse.setPrice(150.0f);
        testCourse.setTimeSlot(1);
        testCourse = courseRepository.save(testCourse);  // Save course to database

        // Create two registrations for the same course and week
        registration1 = new Registration();
        registration1.setCourse(testCourse); // Associate with persisted course
        registration1.setNumWeek(1);

        registration2 = new Registration();
        registration2.setCourse(testCourse); // Associate with persisted course
        registration2.setNumWeek(1);

        // Save to the repository
        registrationRepository.save(registration1);
        registrationRepository.save(registration2);

        // Ensure both registrations have been saved
        Assertions.assertNotNull(registration1.getNumRegistration(), "Registration 1 should have a valid ID.");
        Assertions.assertNotNull(registration2.getNumRegistration(), "Registration 2 should have a valid ID.");
    }

   /* @Test
    @Order(1)
    public void countByCourseAndNumWeekTest() {
        // Verify the count for the specific course and week
        long count = registrationRepository.countByCourseAndNumWeek(testCourse, 1);
        Assertions.assertEquals(2, count, "The count should be 2 for the given course and week.");
    }*/

    @Test
    @Order(2)
    public void testCountWithNoRegistrations() {
        Course anotherCourse = new Course();
        anotherCourse.setLevel(2);
        anotherCourse.setSupport(Support.SNOWBOARD);
        anotherCourse.setPrice(200.0f);
        anotherCourse.setTimeSlot(2);
        courseRepository.save(anotherCourse); // Persist another course

        long count = registrationRepository.countByCourseAndNumWeek(anotherCourse, 1);
        Assertions.assertEquals(0, count, "The count should be 0 for a course with no registrations.");
    }

    @Test
    @Order(3) // Adjust the order as needed
    public void countDistinctByNumWeekAndSkierAndCourseTest() {
        // Create and save the test data
        Course testCourse = new Course();
        testCourse.setLevel(1);
        testCourse.setSupport(Support.SKI);
        testCourse.setPrice(150.0f);
        testCourse.setTimeSlot(1);
        testCourse = courseRepository.save(testCourse);

        // Create a skier
        Skier testSkier = new Skier();
        testSkier.setFirstName("Test Skier");
        testSkier = skierRepository.save(testSkier); // Save skier

        // Create registrations for the skier
        Registration registration1 = new Registration();
        registration1.setCourse(testCourse);
        registration1.setNumWeek(1);
        registration1.setSkier(testSkier);
        registrationRepository.save(registration1);

        Registration registration2 = new Registration();
        registration2.setCourse(testCourse);
        registration2.setNumWeek(1);
        registration2.setSkier(testSkier);
        registrationRepository.save(registration2); // Same skier and week

        // Now, count distinct registrations
        long count = registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, testSkier.getNumSkier(), testCourse.getNumCourse());
        Assertions.assertEquals(2, count, "The count should be 2 for the given skier and course in the same week.");

        // Test for a case with no registrations for different skier/course/week
        long zeroCount = registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(1, 999L, testCourse.getNumCourse());
        Assertions.assertEquals(0, zeroCount, "The count should be 0 for a non-existent skier.");
    }

    // Add any additional tests below if necessary

}
