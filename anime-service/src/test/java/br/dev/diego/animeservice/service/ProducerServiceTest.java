package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.commons.ProducertUtils;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerRepository repository;

    private final List<Producer> producerList = new ArrayList<>();

    private Producer producer1;

    @BeforeEach
    void setUp() {
        producer1 = ProducertUtils.createProducer();
        producerList.addAll(ProducertUtils.createProducerList());
    }

    @Test
    @DisplayName("Deve retornar todos producers quando o nome estiver nulo")
    @Order(1)
    void findAll_deve_buscar_todos_producers() {
        when(repository.findAll()).thenReturn(producerList);
        List<Producer> producers = service.buscar(null);
        assertFalse(producers.isEmpty());
        assertEquals(4, producers.size());
        assertThat(producers).hasSameElementsAs(producerList);
    }

    @Test
    @DisplayName("Deve retornar apenas 1 producer quando o nome é encontrado")
    @Order(2)
    void findAll_deve_buscar_um_producer() {
        when(repository.findAll()).thenReturn(producerList);
        when(repository.findByName("Toei Animation")).thenReturn(List.of(producer1));
        List<Producer> producers = service.buscar("Toei Animation");
        assertFalse(producers.isEmpty());
        assertEquals(1, producers.size());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o nome não é encontrado")
    @Order(3)
    void findAll_deve_retornar_lista_vazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Producer> producers = service.buscar(null);
        assertTrue(producers.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve salvar um producer")
    @Order(4)
    void save_deve_salvar_um_producer() {
        Producer producer = new Producer(6L, "New Producer", LocalDateTime.now());
        when(repository.save(producer)).thenReturn(producer);
        Producer savedProducer = service.save(producer);
        assertEquals(producer, savedProducer);
        verify(repository, times(1)).save(producer);
    }

    @Test
    @DisplayName("Deve buscar um producer por ID")
    @Order(5)
    void buscarPorId_deve_retornar_um_producer() {
        Producer producer = new Producer(6L, "New Producer", LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(producer);
        Producer foundProducer = service.buscarPorId(1L);
        assertEquals(producer, foundProducer);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar um producer")
    @Order(6)
    void atualizar_deve_atualizar_um_producer() {
        Producer producer = new Producer(6L, "New Producer", LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(producer);
        Producer updatedProducer = service.atualizar(1L);
        assertEquals(producer, updatedProducer);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve deletar um producer por ID")
    @Order(7)
    void deletar_deve_deletar_um_producer() {
        doNothing().when(repository).deleteById(1L);
        service.deletar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lancar uma excecao quando nao encontra o producer")
    @Order(8)
    void deletar_deve_lancar_ResponseStatusException() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found")).when(repository).deleteById(1L);

        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.deletar(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

}