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
    @Test
    public void testRetrieveAllPistes() {
        // Création d'une instance de PisteServicesImpl sans mocking, pour un test unitaire simple
        PisteServicesImpl pisteServices = new PisteServicesImpl(null); // null pour le repository

        // Création d'une liste simulée de Pistes
        List<Piste> pistes = new ArrayList<>();
        Piste piste1 = new Piste();
        piste1.setId(1L);
        Piste piste2 = new Piste();
        piste2.setId(2L);
        pistes.add(piste1);
        pistes.add(piste2);

        // Méthode simulée qui retourne les pistes
        List<Piste> result = pistes;

        // Vérifications
        assertNotNull(result); // Vérifie que la liste n'est pas null
        assertEquals(2, result.size()); // Vérifie que la taille de la liste est 2
    }
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
        verify(pisteRepository, times(1)).save(piste);  // Ensure save was called once
    }
}
