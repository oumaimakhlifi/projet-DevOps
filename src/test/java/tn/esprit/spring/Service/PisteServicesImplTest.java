import org.mockito.Mockito;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.ArrayList;
import java.util.List;

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

        // Assert using simple assertions
        assert result != null : "Result should not be null";
        assert result.size() == 2 : "Result size should be 2";
        assert result.get(0).getId() == 1L : "First piste ID should be 1";
        assert result.get(1).getId() == 2L : "Second piste ID should be 2";
        verify(pisteRepository, times(1)).findAll();
    }

    public void testAddPiste() {
        Piste piste = new Piste();
        piste.setNumPiste(1L);
        piste.setNamePiste("Test Piste");

        when(pisteRepository.save(any(Piste.class))).thenReturn(piste);

        // Act
        Piste result = pisteService.addPiste(piste);

        // Assert using simple assertions
        assert result != null : "Result should not be null";
        assert "Test Piste".equals(result.getNamePiste()) : "Piste name should be 'Test Piste'";
        verify(pisteRepository, times(1)).save(piste);
    }

    public void testRemovePiste() {
        Long numPiste = 1L;

        // Act
        pisteService.removePiste(numPiste);

        // Assert
        verify(pisteRepository, times(1)).deleteById(numPiste);
    }
}
