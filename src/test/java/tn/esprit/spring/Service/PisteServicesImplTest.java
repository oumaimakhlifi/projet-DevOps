import org.mockito.Mockito;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PisteServicesImplTest {

    private IPisteRepository pisteRepository;
    private PisteServicesImpl pisteService;

    public static void main(String[] args) {
        PisteServicesImplTest test = new PisteServicesImplTest();
        test.setUp();
        test.testRetrieveAllPistes();
        test.testAddPiste();
        test.testRemovePiste();
        test.testGetPisteById();
    }

    public void setUp() {
        // Create a mock for the PisteRepository
        pisteRepository = Mockito.mock(IPisteRepository.class);
        // Initialize the PisteServicesImpl with the mock repository
        pisteService = new PisteServicesImpl(pisteRepository);
    }

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

        // Assert using Mockito
        assert result != null : "Result should not be null";
        assert result.size() == 2 : "Result size should be 2";
        verify(pisteRepository, times(1)).findAll();
    }

    public void testAddPiste() {
        Piste piste = new Piste();
        piste.setNumPiste(1L);
        piste.setNamePiste("Test Piste");

        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        Piste result = pisteService.addPiste(piste);

        assert result != null : "Result should not be null";
        assert "Test Piste".equals(result.getNamePiste()) : "Piste name should be 'Test Piste'";
        verify(pisteRepository, times(1)).save(piste);
    }

    public void testRemovePiste() {
        Long numPiste = 1L;
        pisteService.removePiste(numPiste);
        verify(pisteRepository, times(1)).deleteById(numPiste);
    }

    public void testGetPisteById() {
        Long numPiste = 1L;
        Piste mockPiste = new Piste();
        mockPiste.setId(numPiste);
        mockPiste.setNamePiste("Mock Piste");

        // Mock the behavior of the repository
        when(pisteRepository.findById(numPiste)).thenReturn(Optional.of(mockPiste));

        // Act
        Piste result = pisteService.retrievePiste(numPiste);

        // Assert
        assert result != null : "Result should not be null";
        assert result.getId().equals(numPiste) : "Piste ID should match the requested ID";
        assert "Mock Piste".equals(result.getNamePiste()) : "Piste name should be 'Mock Piste'";
        verify(pisteRepository, times(1)).findById(numPiste);
    }
}
