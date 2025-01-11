package br.dev.diego.animeservice.domain.request;

public class AnimeRequest {

    private String name;

    public AnimeRequest() {
    }

    public AnimeRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
