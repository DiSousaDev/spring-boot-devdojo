package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.commons.AnimeUtils;
import br.dev.diego.animeservice.commons.JsonUtils;
import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.repository.AnimeData;
import br.dev.diego.animeservice.repository.AnimeRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "br.dev.diego.animeservice")
class AnimeControllerTest {

    public static final String PATH = "/v1/animes";
    public static final String ANIME_GET_ANIME_NULL_NAME_200_JSON = "anime/get-anime-null-name-200.json";
    public static final String ANIME_GET_ANIME_NARUTO_NAME_200_JSON = "anime/get-anime-naruto-name-200.json";
    public static final String ANIME_GET_ANIME_ID_1_200_JSON = "anime/get-anime-id-1-200.json";
    public static final String ANIME_POST_REQUEST_ANIME_200_JSON = "anime/post-request-anime-200.json";
    public static final String ANIME_POST_RESPONSE_ANIME_201_JSON = "anime/post-response-anime-201.json";
    public static final String ANIME_PUT_REQUEST_ANIME_200_JSON = "anime/put-request-anime-200.json";
    public static final String ANIME_PUT_RESPONSE_ANIME_200_JSON = "anime/put-response-anime-200.json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtils jsonUtils;

    @MockBean
    private AnimeData animeData;

    @SpyBean //Permite mocar apenas uma chamada específica ao método.
    private AnimeRepository repository;

    private final List<Anime> animeList = new ArrayList<>();

    private Anime anime1;

    @BeforeEach
    void setUp() {
        anime1 = AnimeUtils.createAnime();
        animeList.addAll(AnimeUtils.createAnimeList());
    }

    @Test
    @DisplayName("GET v1/animes Deve retornar todos animes quando o nome estiver nulo")
    @Order(1)
    void findAll_deve_buscar_todos_animes() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);
        String response = jsonUtils.loadJson(ANIME_GET_ANIME_NULL_NAME_200_JSON);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?nome=Naruto Deve retornar apenas 1 producer quando o nome é encontrado")
    @Order(2)
    void findAll_deve_buscar_um_producer() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);
        String response = jsonUtils.loadJson(ANIME_GET_ANIME_NARUTO_NAME_200_JSON);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("nome", "Naruto"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?nome=xxx Deve retornar uma lista vazia quando o nome não é encontrado")
    @Order(3)
    void findAll_deve_retornar_lista_vazia() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH).param("nome", "xxx"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DisplayName("GET v1/animes/1 Deve buscar um producer por ID")
    @Order(4)
    void buscarPorId_deve_retornar_um_anime() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);
        String response = jsonUtils.loadJson(ANIME_GET_ANIME_ID_1_200_JSON);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/99 Deve retornar um ResponseStatusException 404 quando ID nao encontrado")
    @Order(5)
    void buscarPorId_deve_retornar_um_erro_404_ResponseStatusException() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", 99))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /animes Deve salvar um anime")
    @Order(6)
    void deve_salvar_um_anime() throws Exception {
        String request = jsonUtils.loadJson(ANIME_POST_REQUEST_ANIME_200_JSON);
        String response = jsonUtils.loadJson(ANIME_POST_RESPONSE_ANIME_201_JSON);
        Anime animeToSave = new Anime(67L, "New Anime");
        when(repository.save(any())).thenReturn(animeToSave);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/animes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("PUT /animes Deve atualizar um anime")
    @Order(7)
    void atualizar_deve_atualizar_um_anime() throws Exception {
        String request = jsonUtils.loadJson(ANIME_PUT_REQUEST_ANIME_200_JSON);
        String response = jsonUtils.loadJson(ANIME_PUT_RESPONSE_ANIME_200_JSON);
        Anime animeToUpdate = new Anime(1L, "Anime updated");
        when(animeData.getAnimes()).thenReturn(animeList);
        when(repository.save(any())).thenReturn(animeToUpdate);

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
    @DisplayName("PUT v1/animes/99 Deve retornar um ResponseStatusException 404 quando ID nao encontrado")
    @Order(8)
    void atualizar_deve_retornar_um_erro_404_ResponseStatusException() throws Exception {
        String request = jsonUtils.loadJson(ANIME_PUT_REQUEST_ANIME_200_JSON);
        when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(PATH + "/{id}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("DELETE v1/animes/1 Deve deletar um anime por ID")
    @Order(9)
    void deletar_deve_deletar_um_anime() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/animes/1 Deve lancar uma excecao quando nao encontra o producer")
    @Order(10)
    void deletar_deve_lancar_ResponseStatusException() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/{id}", 99))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}