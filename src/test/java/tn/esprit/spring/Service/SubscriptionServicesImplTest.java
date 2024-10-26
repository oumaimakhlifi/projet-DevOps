package tn.esprit.spring.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SubscriptionServicesImplTest {

    private ISubscriptionRepository subscriptionRepository;
    private SubscriptionServicesImpl subscriptionService;

    @BeforeEach
    public void setUp() {
        // Create a mock for the SubscriptionRepository
        subscriptionRepository = mock(ISubscriptionRepository.class);
        subscriptionService = new SubscriptionServicesImpl(subscriptionRepository, null); // null for the skierRepository
    }

    @Test
    public void testAddSubscription() {
        // Arrange
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L);
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());
        subscription.setPrice(100f);

        // Mock the behavior of the subscriptionRepository
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

        // Act
        Subscription result = subscriptionService.addSubscription(subscription);

        // Assert
        assertNotNull(result);
        assertEquals(TypeSubscription.ANNUAL, result.getTypeSub());

        // Verify the interaction with the repository
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    public void testGetSubscriptionByType() {
        // Arrange
        Set<Subscription> subscriptions = new HashSet<>();
        subscriptions.add(new Subscription());
        when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(any())).thenReturn(subscriptions);

        // Act
        Set<Subscription> result = subscriptionService.getSubscriptionByType(TypeSubscription.MONTHLY);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }


}
