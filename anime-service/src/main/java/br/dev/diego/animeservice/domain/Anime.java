package br.dev.diego.animeservice.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Anime {

    private Long id;
    private String name;

    private static List<Anime> animes = new ArrayList<>();

    static {
        animes.addAll(Arrays.asList(
                new Anime(1L, "Naruto"),
                new Anime(2L, "One Piece"),
                new Anime(3L, "Attack on Titan"),
                new Anime(4L, "My Hero Academia"),
                new Anime(5L, "Demon Slayer")));
    }

    public Anime() {
    }

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}
