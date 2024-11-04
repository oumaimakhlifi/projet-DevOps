package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ISkierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SkierServiceImplTest {

    @Mock
    private ISkierRepository skierRepository;

    @InjectMocks
    private SkierServicesImpl skierService;

    // List to simulate the database
    private List<Skier> skierList;

    // Test objects
    private Skier skier1;
    private Skier skier2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        skierList = new ArrayList<>();

        // Create Skieur objects for testing
        skier1 = new Skier();
        skier1.setNumSkier(1L);
        skier1.setFirstName("skieur1firstname");
        skier1.setLastName("skieur2lastname");
        skier1.setCity("Tunis");

        skier2 = new Skier();
        skier2.setNumSkier(2L);
        skier2.setFirstName("skieur2firstname");
        skier2.setLastName("skieur2lastname");
        skier2.setCity("Alger");
    }

    // Test for adding a Skieur (Create)
    @Test
    public void testAddSkier() {
        // Simuler le comportement de save avec Mockito
        when(skierRepository.save(any(Skier.class))).thenAnswer(invocation -> {
            Skier savedSkier = invocation.getArgument(0);
            savedSkier.setNumSkier(1L); // Simule un ID auto-généré
            return savedSkier;
        });

        // Appel de la méthode à tester
        Skier addedSkier = skierService.addSkier(skier1);

        // Assertions pour vérifier que le cours a été ajouté correctement
        assertNotNull(addedSkier, "Le skieur ajouté ne doit pas être null");
        assertEquals(skier1.getNumSkier(), addedSkier.getNumSkier(), "Le numéro du skieur ajouté est incorrect");
        assertEquals(1, addedSkier.getNumSkier(), "L'ID du skieur ajouté doit être 1");
        verify(skierRepository, times(1)).save(skier1); // Vérifie que save a été appelé une fois
    }

    // Test for retrieving a skieur (Read)
    @Test
    public void testRetrieveSkier() {
        Long skierId = 1L;

        // Configuration du mock pour simuler la présence du cours
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier1));

        // Appel de la méthode à tester
        Skier retrievedSkier = skierService.retrieveSkier(skierId);

        // Assertions pour vérifier que le cours a été récupéré correctement
        assertNotNull(retrievedSkier, "Le skieur récupéré ne doit pas être null");
        assertEquals(skierId, retrievedSkier.getNumSkier(), "L'ID du skieur récupéré est incorrect");
        assertEquals(skier1.getNumSkier(), retrievedSkier.getNumSkier(), "Le numéro du skieur récupéré est incorrect");
        verify(skierRepository, times(1)).findById(skierId); // Vérifie que findById a été appelé
    }



    // Test for updating a skieur
    @Test
    void testUpdateSkier() {
        // Configuration des comportements des mocks
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier1));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier1);

        // Appel de la méthode à tester
        Skier updatedSkier = skierService.updateSkier(skier1);

        // Assertions pour vérifier que le résultat est correct
        assertNotNull(updatedSkier, "Le skieur mis à jour ne doit pas être null");
        assertEquals(skier1.getNumSkier(), updatedSkier.getNumSkier(), "Le numéro du skieur mis à jour est incorrect");
        verify(skierRepository, times(1)).save(skier1); // Vérifier que save a été appelé
    }

    // Test for deleting a skieur
    @Test
    void testDeleteSkier() {
        Long skierId = 1L;

        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier1));

        skierService.removeSkier(skierId);

        verify(skierRepository, times(1)).delete(skier1);
    }

    // Test for retrieving all skieurs
    @Test
    public void testRetrieveAllSkiers() {
        skierList.add(skier1);
        when(skierRepository.findAll()).thenReturn(skierList);

        List<Skier> allSkiers = skierService.retrieveAllSkiers();

        assertEquals(1, allSkiers.size());
        assertTrue(allSkiers.contains(skier1), "Le skieur devrait être présent dans la liste.");
    }
}