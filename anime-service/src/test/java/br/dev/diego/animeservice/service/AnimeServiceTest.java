package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.commons.AnimeUtils;
import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.repository.AnimeRepository;
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
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;

    @Mock
    private AnimeRepository repository;

    private final List<Anime> animeList = new ArrayList<>();

    private Anime anime1;

    @BeforeEach
    void setUp() {
        anime1 = AnimeUtils.createAnime();
        animeList.addAll(AnimeUtils.createAnimeList());
    }

    @Test
    @DisplayName("Deve retornar todos animes quando o nome estiver nulo")
    @Order(1)
    void findAll_deve_buscar_todos_animes() {
        when(repository.findAll()).thenReturn(animeList);
        List<Anime> animes = service.buscar(null);
        assertFalse(animes.isEmpty());
        assertEquals(4, animes.size());
        assertThat(animes).hasSameElementsAs(animeList);
    }

    @Test
    @DisplayName("Deve retornar apenas 1 anime quando o nome é encontrado")
    @Order(2)
    void findAll_deve_buscar_um_anime() {
        when(repository.findAll()).thenReturn(animeList);
        when(repository.findByName("Toei Animation")).thenReturn(List.of(anime1));
        List<Anime> animes = service.buscar("Toei Animation");
        assertFalse(animes.isEmpty());
        assertEquals(1, animes.size());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o nome não é encontrado")
    @Order(3)
    void findAll_deve_retornar_lista_vazia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Anime> animes = service.buscar(null);
        assertTrue(animes.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve salvar um anime")
    @Order(4)
    void save_deve_salvar_um_anime() {
        Anime anime = new Anime(6L, "New Anime");
        when(repository.save(anime)).thenReturn(anime);
        Anime savedAnime = service.save(anime);
        assertEquals(anime, savedAnime);
        verify(repository, times(1)).save(anime);
    }

    @Test
    @DisplayName("Deve buscar um anime por ID")
    @Order(5)
    void buscarPorId_deve_retornar_um_anime() {
        Anime anime = new Anime(6L, "New Anime");
        when(repository.findById(1L)).thenReturn(anime);
        Anime foundAnime = service.buscarPorId(1L);
        assertEquals(anime, foundAnime);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve atualizar um anime")
    @Order(6)
    void atualizar_deve_atualizar_um_anime() {
        Anime anime = new Anime(6L, "New Anime");
        when(repository.findById(1L)).thenReturn(anime);
        Anime updatedAnime = service.atualizar(1L);
        assertEquals(anime, updatedAnime);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve deletar um anime por ID")
    @Order(7)
    void deletar_deve_deletar_um_anime() {
        doNothing().when(repository).deleteById(1L);
        service.deletar(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lancar uma excecao quando nao encontra o anime")
    @Order(8)
    void deletar_deve_lancar_ResponseStatusException() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found")).when(repository).deleteById(1L);

        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class, () -> {
            service.deletar(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

}