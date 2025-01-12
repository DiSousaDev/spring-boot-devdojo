package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.mappers.ProducerMapperImpl;
import br.dev.diego.animeservice.repository.ProducerData;
import br.dev.diego.animeservice.repository.ProducerRepository;
import br.dev.diego.animeservice.service.ProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({ProducerMapperImpl.class, ProducerService.class, ProducerRepository.class, ProducerData.class})
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private ProducerData producerData;

    private final List<Producer> producerList = new ArrayList<>();

    private Producer producer1;

    @BeforeEach
    void setUp() {
        LocalDateTime localDateTime = LocalDateTime.parse("2025-01-10T17:48:37.550866");

        producer1 = new Producer(1L, "Bones", localDateTime);
        Producer producer2 = new Producer(2L, "Madhouse", localDateTime);
        Producer producer3 = new Producer(3L, "Toei Animation Test", localDateTime);
        Producer producer4 = new Producer(4L, "Sunrise", localDateTime);
        producerList.addAll(List.of(producer1, producer2, producer3, producer4));
    }

    @Test
    @DisplayName("GET v1/producers Deve retornar todos producers quando o nome estiver nulo")
    @Order(1)
    void findAll_deve_buscar_todos_producers() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        String response = loadJson("producer/get-producer-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?nome=Bones Deve retornar apenas 1 producer quando o nome é encontrado")
    @Order(2)
    void findAll_deve_buscar_um_producer() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        String response = loadJson("producer/get-producer-bones-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers").param("nome", "Bones"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?nome=xxx Deve retornar uma lista vazia quando o nome não é encontrado")
    @Order(3)
    void findAll_deve_retornar_lista_vazia() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers").param("nome", "xxx"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DisplayName("GET v1/producers/1 Deve buscar um producer por ID")
    @Order(4)
    void buscarPorId_deve_retornar_um_producer() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        String response = loadJson("producer/get-producer-id-1-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/{id}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/99 Deve retornar um ResponseStatusException 404 quando ID nao encontrado")
    @Order(5)
    void buscarPorId_deve_retornar_um_erro_404_ResponseStatusException() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/producers/{id}", 99))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    private String loadJson(String fileName) {
        try {
            return new String(resourceLoader.getResource("classpath:" + fileName).getInputStream().readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}