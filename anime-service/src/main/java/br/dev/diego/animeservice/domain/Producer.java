package br.dev.diego.animeservice.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Producer {

    private Long id;
    private String name;

    private static List<Producer> producers = new ArrayList<>();

    static {
        producers.addAll(Arrays.asList(
                new Producer(1L, "Toei Animation"),
                new Producer(2L, "Madhouse"),
                new Producer(3L, "Bones"),
                new Producer(4L, "Sunrise"),
                new Producer(5L, "Studio Ghibli")));
    }

    public Producer() {
    }

    public Producer(Long id, String name) {
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

    public static List<Producer> getProducers() {
        return producers;
    }
}
