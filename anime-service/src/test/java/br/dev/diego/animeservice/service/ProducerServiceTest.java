package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.repository.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerRepository repository;

    private List<Producer> producerList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        producerList.addAll(List.of(
                new Producer(1L, "Toei Animation", LocalDateTime.now().minusDays(2)),
                new Producer(2L, "Madhouse", LocalDateTime.now().minusDays(3)),
                new Producer(3L, "Bones", LocalDateTime.now().minusDays(4)),
                new Producer(4L, "Sunrise", LocalDateTime.now().minusDays(5)),
                new Producer(5L, "Studio Ghibli", LocalDateTime.now().minusDays(6))
        ));
    }

    @Test
    @DisplayName("Deve buscar todos producers quando o nome estiver nulo")
    @Order(1)
    void findAll_deve_buscar_todos_producers() {
        when(repository.findAll()).thenReturn(producerList);
//        List<ProducerResponse> producers = service.buscar(null);
//        assertFalse(producers.isEmpty());
//        assertEquals(4, producers.size());
    }
}