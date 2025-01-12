package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.commons.AnimeUtils;
import br.dev.diego.animeservice.domain.Anime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimeRepositoryTest {

    @InjectMocks
    private AnimeRepository repository;

    @Mock
    private AnimeData animeData;

    private final List<Anime> animeList = new ArrayList<>();

    private Anime anime1;

    @BeforeEach
    void setUp() {
        anime1 = AnimeUtils.createAnime();
        animeList.addAll(AnimeUtils.createAnimeList());
    }

    @Test
    void findAll_deve_buscar_todos_animes_quando_sucesso() {
        when(animeData.getAnimes()).thenReturn(animeList);
        List<Anime> animes = repository.findAll();
        assertFalse(animes.isEmpty());
        assertEquals(4, animes.size());
        assertThat(animes).hasSameElementsAs(this.animeList);
    }

    @Test
    void save_deve_salvar_um_anime_quando_sucesso() {
        when(animeData.getAnimes()).thenReturn(animeList);
        Anime anime = new Anime(5L, "New Anime");
        repository.save(anime);
        List<Anime> animes = repository.findAll();
        assertFalse(animes.isEmpty());
        assertEquals(5, animes.size());
        assertThat(animes).contains(anime);
    }

    @Test
    void findById_deve_retornar_um_anime() {
        when(animeData.getAnimes()).thenReturn(animeList);
        Anime producer = repository.findById(1L);
        assertNotNull(producer);
        assertEquals(anime1, producer);
    }

    @Test
    void findByName_deve_retornar_uma_lista_com_anime_caso_o_nome_exista() {
        when(animeData.getAnimes()).thenReturn(animeList);
        List<Anime> animes = repository.findByName("Naruto Test");
        assertFalse(animes.isEmpty());
        assertEquals(1, animes.size());
        assertThat(animes).hasSameElementsAs(List.of(anime1));
    }

    @Test
    void findByName_deve_retornar_uma_lista_vazia_quando_o_nome_nao_existe() {
        when(animeData.getAnimes()).thenReturn(animeList);
        List<Anime> animes = repository.findByName("Sem Nome");
        assertTrue(animes.isEmpty());
    }

    @Test
    void deleteById_deve_remover_o_anime_da_lista() {
        when(animeData.getAnimes()).thenReturn(animeList);
        repository.deleteById(1L);
        List<Anime> animes = repository.findAll();
        assertFalse(animes.isEmpty());
        assertEquals(3, animes.size());
        assertThat(animes).doesNotContain(anime1);
    }

}
