package br.dev.diego.animeservice.commons;

import br.dev.diego.animeservice.domain.Anime;

import java.util.List;

public class AnimeUtils {

    public static Anime createAnime() {
        return new Anime(1L, "Naruto Test");
    }

    public static List<Anime> createAnimeList() {
        return List.of(
                createAnime(),
                new Anime(2L, "One Piece Test"),
                new Anime(3L, "Attack on Titan Test"),
                new Anime(4L, "My Hero Academia Test")
        );
    }

}
