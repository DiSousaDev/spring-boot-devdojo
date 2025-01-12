package br.dev.diego.animeservice.domain;

import java.util.Objects;

public class Anime {

    private Long id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Anime anime)) return false;
        return Objects.equals(id, anime.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
