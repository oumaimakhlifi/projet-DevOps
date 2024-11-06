package tn.esprit.spring.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PisteServicesImplTest {

    private IPisteRepository pisteRepository;
    private PisteServicesImpl pisteService;

    @BeforeEach
    public void setUp() {
        // Create a mock for the PisteRepository
        pisteRepository = Mockito.mock(IPisteRepository.class);
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

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Result size should be 2");
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    public void testAddPiste() {
        Piste piste = new Piste();
        piste.setNumPiste(1L);
        piste.setNamePiste("Test Piste");

        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste result = pisteService.addPiste(piste);

        assertNotNull(result, "Result should not be null");
        assertEquals("Test Piste", result.getNamePiste(), "Piste name should be 'Test Piste'");
        verify(pisteRepository, times(1)).save(piste);
    }

    @Test
    public void testRemovePiste() {
        Long numPiste = 1L;
        pisteService.removePiste(numPiste);
        verify(pisteRepository, times(1)).deleteById(numPiste);
    }
}
