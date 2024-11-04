/*package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Skieur;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.repositories.ISkieurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SkieurServiceImplTest {

    @Mock
    private ISkierRepository skieurRepository;

    @InjectMocks
    private SkierServicesImpl skieurService;

    // List to simulate the database
    private List<Skier> skieurList;

    // Test objects
    private Skier skieur1;
    private SKier skieur2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        skieurList = new ArrayList<>();

        // Create Skieur objects for testing
        skieur1 = new Skier();
        skieur1.setNumSkier(1);
        skieur1.setFirstName("skieur1firstname");
        skieur1.setLastName("skieur2lastname");
        skieur1.setDateOfBirth("22/11/2001");
        skieur1.setCity("Tunis");

        skieur2 = new Skier();
        skieur2.setNumSkier(2);
        skieur2.setFirstName("skieur2firstname");
        skieur2.setLastName("skieur2lastname");
        skieur2.setDateOfBirth("02/01/2002");
        skieur2.setCity("Alger");
    }

    // Test for adding a Skieur (Create)
    @Test
    public void testAddSkier() {
        // Simuler le comportement de save avec Mockito
        when(skieurRepository.save(any(Skier.class))).thenAnswer(invocation -> {
            Skier savedSkieur = invocation.getArgument(0);
            savedSkieur.setNumSkier(1); // Simule un ID auto-généré
            return savedSkieur;
        });

        // Appel de la méthode à tester
        Skier addedSkieur = skieurService.addSkier(skieur1);

        // Assertions pour vérifier que le cours a été ajouté correctement
        assertNotNull(addedSkieur, "Le skieur ajouté ne doit pas être null");
        assertEquals(skieur1.getNumSkier(), addedSkieur.getNumSkier(), "Le numéro du skieur ajouté est incorrect");
        assertEquals(1, addedSkieur.getNumSkier(), "L'ID du skieur ajouté doit être 1");
        verify(skieurRepository, times(1)).save(skieur1); // Vérifie que save a été appelé une fois
    }

    // Test for retrieving a skieur (Read)
    @Test
    public void testRetrieveSkier() {
        Long skieurId = 1;

        // Configuration du mock pour simuler la présence du cours
        when(skieurRepository.findById(skieurId)).thenReturn(Optional.of(skieur1));

        // Appel de la méthode à tester
        Skier retrievedSkier = skieurService.retrieveSkier(skieurId);

        // Assertions pour vérifier que le cours a été récupéré correctement
        assertNotNull(retrievedSkier, "Le skieur récupéré ne doit pas être null");
        assertEquals(skieurId, retrievedSkier.getNumSkier(), "L'ID du skieur récupéré est incorrect");
        assertEquals(skieur1.getNumSkier(), retrievedSkier.getNumSkier(), "Le numéro du skieur récupéré est incorrect");
        verify(skieurRepository, times(1)).findById(skieurId); // Vérifie que findById a été appelé
    }



    // Test for updating a skieur
    @Test
    void testUpdateSkier() {
        // Configuration des comportements des mocks
        when(skieurRepository.findById(1)).thenReturn(Optional.of(skieur1));
        when(skieurRepository.save(any(Skier.class))).thenReturn(skieur1);

        // Appel de la méthode à tester
        Skier updatedSkieur = skieurService.updateSkier(skieur1);

        // Assertions pour vérifier que le résultat est correct
        assertNotNull(updatedSkieur, "Le skieur mis à jour ne doit pas être null");
        assertEquals(skieur1.getNumSkier(), updatedCourse.getNumSkier(), "Le numéro du skieur mis à jour est incorrect");
        verify(skieurRepository, times(1)).save(skieur1); // Vérifier que save a été appelé
    }

    // Test for deleting a skieur
    @Test
    void testDeleteSkier() {
        Long skieurId = 1;

        when(skieurRepository.findById(skieurId)).thenReturn(Optional.of(skieur1));

        skieurService.deleteSkier(skieurId);

        verify(skieurRepository, times(1)).delete(skieur1);
    }

    // Test for retrieving all skieurs
    @Test
    public void testRetrieveAllSkiers() {
        skieurList.add(skieur1);
        when(skieurRepository.findAll()).thenReturn(skieurList);

        List<Skier> allSkieurs = skieurService.retrieveAllSkiers();

        assertEquals(1, allSkieurs.size());
        assertTrue(allSkieurs.contains(skieur1), "Le skieur devrait être présent dans la liste.");
    }
}*/