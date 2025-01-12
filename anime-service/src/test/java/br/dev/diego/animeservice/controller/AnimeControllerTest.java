package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.mappers.AnimeMapperImpl;
import br.dev.diego.animeservice.repository.AnimeData;
import br.dev.diego.animeservice.repository.AnimeRepository;
import br.dev.diego.animeservice.service.AnimeService;
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
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
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
@Import({AnimeMapperImpl.class, AnimeService.class, AnimeRepository.class, AnimeData.class})
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private AnimeData animeData;

    @SpyBean //Permite mocar apenas uma chamada específica ao método.
    private AnimeRepository repository;

    private final List<Anime> animeList = new ArrayList<>();

    private Anime anime1;

    @BeforeEach
    void setUp() {
        anime1 = new Anime(1L, "Naruto Test");
        Anime anime2 = new Anime(2L, "One Piece Test");
        Anime anime3 = new Anime(3L, "Attack on Titan Test");
        Anime anime4 = new Anime(4L, "My Hero Academia Test");
        animeList.addAll(List.of(anime1, anime2, anime3, anime4));
    }

    @Test
    @DisplayName("GET v1/animes Deve retornar todos animes quando o nome estiver nulo")
    @Order(1)
    void findAll_deve_buscar_todos_animes() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);
        String response = loadJson("anime/get-anime-null-name-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes?nome=Naruto Deve retornar apenas 1 producer quando o nome é encontrado")
    @Order(2)
    void findAll_deve_buscar_um_producer() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);
        String response = loadJson("anime/get-anime-naruto-name-200.json");

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
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("nome", "xxx"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    @DisplayName("GET v1/animes/1 Deve buscar um producer por ID")
    @Order(4)
    void buscarPorId_deve_retornar_um_anime() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);
        String response = loadJson("anime/get-anime-id-1-200.json");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("GET v1/animes/99 Deve retornar um ResponseStatusException 404 quando ID nao encontrado")
    @Order(5)
    void buscarPorId_deve_retornar_um_erro_404_ResponseStatusException() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", 99))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("POST /animes Deve salvar um anime")
    @Order(6)
    void deve_salvar_um_anime() throws Exception {
        String request = loadJson("anime/post-request-anime-200.json");
        String response = loadJson("anime/post-response-anime-201.json");
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
        String request = loadJson("anime/put-request-anime-200.json");
        String response = loadJson("anime/put-response-anime-200.json");
        Anime animeToUpdate = new Anime(1L, "Anime updated");
        when(animeData.getAnimes()).thenReturn(animeList);
        when(repository.save(any())).thenReturn(animeToUpdate);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/animes/{id}", 1)
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
        String request = loadJson("anime/put-request-anime-200.json");
        when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/animes/{id}", 99)
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

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", 1))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE v1/animes/1 Deve lancar uma excecao quando nao encontra o producer")
    @Order(10)
    void deletar_deve_lancar_ResponseStatusException() throws Exception {
        when(animeData.getAnimes()).thenReturn(animeList);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", 99))
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