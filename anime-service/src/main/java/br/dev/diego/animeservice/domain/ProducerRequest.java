package br.dev.diego.animeservice.domain;

public class ProducerRequest {

    private String name;

    public ProducerRequest() {
    }

    public ProducerRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
