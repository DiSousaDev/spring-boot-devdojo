package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.commons.ProducertUtils;
import br.dev.diego.animeservice.domain.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProducerRepositoryTest {

    @InjectMocks
    private ProducerRepository repository;

    @Mock
    private ProducerData producerData;

    private final List<Producer> producerList = new ArrayList<>();

    private Producer producer1;

    @BeforeEach
    void setUp() {
        producer1 = ProducertUtils.createProducer();
        producerList.addAll(ProducertUtils.createProducerList());
    }

    @Test
    void findAll_deve_buscar_todos_producers_quando_sucesso() {
        when(producerData.getProducers()).thenReturn(producerList);
        List<Producer> producers = repository.findAll();
        assertFalse(producers.isEmpty());
        assertEquals(4, producers.size());
        assertThat(producers).hasSameElementsAs(this.producerList);
    }

    @Test
    void save_deve_salvar_um_producer_quando_sucesso() {
        when(producerData.getProducers()).thenReturn(producerList);
        Producer producer = new Producer(5L, "Studio Ghibli", LocalDateTime.now().minusDays(6));
        repository.save(producer);
        List<Producer> producers = repository.findAll();
        assertFalse(producers.isEmpty());
        assertEquals(5, producers.size());
        assertThat(producers).contains(producer);
    }

    @Test
    void findById_deve_retornar_um_producer() {
        when(producerData.getProducers()).thenReturn(producerList);
        Producer producer = repository.findById(1L);
        assertNotNull(producer);
        assertEquals(producer1, producer);
    }

    @Test
    void findByName_deve_retornar_uma_lista_com_producer_caso_o_nome_exista() {
        when(producerData.getProducers()).thenReturn(producerList);
        List<Producer> producers = repository.findByName("Bones");
        assertFalse(producers.isEmpty());
        assertEquals(1, producers.size());
        assertThat(producers).hasSameElementsAs(List.of(producer1));
    }

    @Test
    void findByName_deve_retornar_uma_lista_vazia_quando_o_nome_nao_existe() {
        when(producerData.getProducers()).thenReturn(producerList);
        List<Producer> producers = repository.findByName("Sem Nome");
        assertTrue(producers.isEmpty());
    }

    @Test
    void deleteById_deve_remover_o_producer_da_lista() {
        when(producerData.getProducers()).thenReturn(producerList);
        repository.deleteById(1L);
        List<Producer> producers = repository.findAll();
        assertFalse(producers.isEmpty());
        assertEquals(3, producers.size());
        assertThat(producers).doesNotContain(producer1);
    }
}