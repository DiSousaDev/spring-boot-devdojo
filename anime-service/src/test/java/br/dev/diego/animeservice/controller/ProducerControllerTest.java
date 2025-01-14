package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.commons.JsonUtils;
import br.dev.diego.animeservice.commons.ProducertUtils;
import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.repository.ProducerData;
import br.dev.diego.animeservice.repository.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "br.dev.diego.animeservice")
class ProducerControllerTest {

    public static final String PATH = "/v1/producers";
    public static final String PRODUCER_GET_PRODUCER_NULL_NAME_200_JSON = "producer/get-producer-null-name-200.json";
    public static final String PRODUCER_GET_PRODUCER_BONES_NAME_200_JSON = "producer/get-producer-bones-name-200.json";
    public static final String PRODUCER_GET_PRODUCER_ID_1_200_JSON = "producer/get-producer-id-1-200.json";
    public static final String PRODUCER_POST_REQUEST_PRODUCER_200_JSON = "producer/post-request-producer-200.json";
    public static final String PRODUCER_POST_RESPONSE_PRODUCER_201_JSON = "producer/post-response-producer-201.json";
    public static final String PRODUCER_PUT_REQUEST_PRODUCER_200_JSON = "producer/put-request-producer-200.json";
    public static final String PRODUCER_PUT_RESPONSE_PRODUCER_200_JSON = "producer/put-response-producer-200.json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private ProducerData producerData;

    @SpyBean //Permite mocar apenas uma chamada específica ao método.
    private ProducerRepository repository;

    private final List<Producer> producerList = new ArrayList<>();

    private Producer producer1;

    @BeforeEach
    void setUp() {
        producer1 = ProducertUtils.createProducer();
        producerList.addAll(ProducertUtils.createProducerList());
    }

    @Test
    @DisplayName("GET v1/producers Deve retornar todos producers quando o nome estiver nulo")
    @Order(1)
    void findAll_deve_buscar_todos_producers() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        String response = jsonUtils.loadJson(PRODUCER_GET_PRODUCER_NULL_NAME_200_JSON);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?nome=Bones Deve retornar apenas 1 producer quando o nome é encontrado")
    @Order(2)
    void findAll_deve_buscar_um_producer() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        String response = jsonUtils.loadJson(PRODUCER_GET_PRODUCER_BONES_NAME_200_JSON);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH).param("nome", "Bones"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers?nome=xxx Deve retornar uma lista vazia quando o nome não é encontrado")
    @Order(3)
    void findAll_deve_retornar_lista_vazia() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH).param("nome", "xxx"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DisplayName("GET v1/producers/1 Deve buscar um producer por ID")
    @Order(4)
    void buscarPorId_deve_retornar_um_producer() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);
        String response = jsonUtils.loadJson(PRODUCER_GET_PRODUCER_ID_1_200_JSON);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/producers/99 Deve retornar um ResponseStatusException 404 quando ID nao encontrado")
    @Order(5)
    void buscarPorId_deve_retornar_um_erro_404_ResponseStatusException() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", 99))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /producers Deve salvar um producer")
    @Order(6)
    void deve_salvar_um_producer() throws Exception {
        String request = jsonUtils.loadJson(PRODUCER_POST_REQUEST_PRODUCER_200_JSON);
        String response = jsonUtils.loadJson(PRODUCER_POST_RESPONSE_PRODUCER_201_JSON);
        Producer producerToSave = new Producer(67L, "New Producer", LocalDateTime.parse("2025-01-10T17:48:37.550866"));
        when(repository.save(any())).thenReturn(producerToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT /producers Deve atualizar um producer")
    @Order(7)
    void atualizar_deve_atualizar_um_producer() throws Exception {
        String request = jsonUtils.loadJson(PRODUCER_PUT_REQUEST_PRODUCER_200_JSON);
        String response = jsonUtils.loadJson(PRODUCER_PUT_RESPONSE_PRODUCER_200_JSON);
        Producer producerToUpdate = new Producer(1L, "Producer updated", LocalDateTime.parse("2025-01-10T17:48:37.550866"));
        when(producerData.getProducers()).thenReturn(producerList);
        when(repository.save(any())).thenReturn(producerToUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(PATH + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT v1/producers/99 Deve retornar um ResponseStatusException 404 quando ID nao encontrado")
    @Order(8)
    void atualizar_deve_retornar_um_erro_404_ResponseStatusException() throws Exception {
        String request = jsonUtils.loadJson(PRODUCER_PUT_REQUEST_PRODUCER_200_JSON);
        when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(PATH + "/{id}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE v1/producers/1 Deve deletar um producer por ID")
    @Order(9)
    void deletar_deve_deletar_um_producer() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/producers/1 Deve lancar uma excecao quando nao encontra o producer")
    @Order(10)
    void deletar_deve_lancar_ResponseStatusException() throws Exception {
        when(producerData.getProducers()).thenReturn(producerList);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", 99))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}