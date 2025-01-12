package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AnimeRepository {

    private static List<Anime> animes = new ArrayList<>();

    static {
        animes.addAll(Arrays.asList(
                new Anime(1L, "Naruto"),
                new Anime(2L, "One Piece"),
                new Anime(3L, "Attack on Titan"),
                new Anime(4L, "My Hero Academia"),
                new Anime(5L, "Demon Slayer")));
    }

    public List<Anime> getAnimes() {
        return animes;
    }

}
