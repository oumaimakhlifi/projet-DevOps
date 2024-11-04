package tn.esprit.spring;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class SkierServiceImpMock {
    @Mock
    ISkierRepository br;
    @InjectMocks
    SkierServicesImpl bs;

    Skier skier1 = new Skier();

    ArrayList<Skier> skiers = new ArrayList();
    Skier skier2 = new Skier();
    Skier skier3 = new Skier();
    SkierServiceImpMock(){
        Long numSkier;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
        String city;
        skier1.setNumSkier(1L);
        skier1.setFirstName("skier a");
        skier1.setLastName("skier a1");
        skier2.setNumSkier(1L);
        skier2.setFirstName("skier a");
        skier2.setLastName("skier a1");
        skier3.setNumSkier(1L);
        skier3.setFirstName("skier a");
        skier3.setLastName("skier a1");
        skiers.add(skier3);
        skiers.add(skier2);
    }

    @Test
    @Order(1)
    void testRetrieveBloc() {
        Mockito.when(br.findById(Mockito.anyLong())).thenReturn(Optional.of(bloc1));
        Skier skier = bs.retrieveSkier(1L);
        Assertions.assertNotNull(skier);
    }

    @Test
    @Order(2)
    void testRetrieveAllBlocs(){
        Mockito.when(br.findAll()).thenReturn(skiers);
        List<Skier> skier1 = bs.retrieveAllSkiers();
        Assertions.assertEquals(2,skier1.size());
    }
/*
    @Test
    @Order(3)
    void testAddBloc() {
        Bloc updatedBloc = new Bloc();
        updatedBloc.setIdBloc(1);
        updatedBloc.setNomBloc("Updated Bloc");
        updatedBloc.setCapaciteBloc(50);

        Mockito.when(br.save(Mockito.any(Bloc.class))).thenReturn(updatedBloc);

        Bloc result = bs.modifyBloc(updatedBloc);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(updatedBloc, result);
    }

    @Test
    @Order(4)
    void testRemoveBloc() {
        Mockito.doNothing().when(br).deleteById(Mockito.anyLong());

        bs.removeBloc(1L);

        Mockito.verify(br, Mockito.times(1)).deleteById(1L);
    }*/

}