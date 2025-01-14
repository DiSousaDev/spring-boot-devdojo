package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AnimeData {

    private final List<Anime> animes = new ArrayList<>();

    {
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
