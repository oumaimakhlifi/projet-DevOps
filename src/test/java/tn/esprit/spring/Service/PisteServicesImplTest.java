package tn.esprit.spring.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PisteServicesImplTest {

    private IPisteRepository pisteRepository;
    private PisteServicesImpl pisteService;

    @BeforeEach
    public void setUp() {
        // Create a mock for the PisteRepository
        pisteRepository = mock(IPisteRepository.class);

        // Initialize the PisteServicesImpl with the mock repository
        pisteService = new PisteServicesImpl(pisteRepository);
    }

    @Test
    public void testRetrieveAllPistes() {
        // Create a list of Pistes
        List<Piste> pistes = new ArrayList<>();
        Piste piste1 = new Piste();
        piste1.setId(1L);
        Piste piste2 = new Piste();
        piste2.setId(2L);
        pistes.add(piste1);
        pistes.add(piste2);

        // Mock the behavior of the repository
        when(pisteRepository.findAll()).thenReturn(pistes);

        // Act
        List<Piste> result = pisteService.retrieveAllPistes();

        // Verify that the result is not null and has the expected size
        assertNotNull(result); // Check that the list is not null
        assertEquals(2, result.size()); // Check that the list size is 2

        // Verify the interaction with the repository
        verify(pisteRepository, times(1)).findAll(); // Ensure findAll was called once
    }

    @Test
    public void testAddPiste() {
        // Arrange
        Piste piste = new Piste();
        piste.setNumPiste(1L);
        piste.setNamePiste("Test Piste");

        // Mock the behavior of the pisteRepository
        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        // Act
        Piste result = pisteService.addPiste(piste);

        // Assert
        assertNotNull(result);
        assertEquals("Test Piste", result.getNamePiste());

        // Verify the interaction with the repository
        verify(pisteRepository, times(1)).save(piste); // Ensure save was called once
    }

    @Test
    public void testRemovePiste() {
        // Arrange
        Long numPiste = 1L;

        // Act
        pisteService.removePiste(numPiste);

        // Assert
        verify(pisteRepository, times(1)).deleteById(numPiste); // Ensure deleteById was called once
    }
}
