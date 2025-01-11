package br.dev.diego.animeservice.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Producer {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

    private static List<Producer> producers = new ArrayList<>();

    static {
        producers.addAll(Arrays.asList(
                new Producer(1L, "Toei Animation", LocalDateTime.now().minusDays(2)),
                new Producer(2L, "Madhouse", LocalDateTime.now().minusDays(3)),
                new Producer(3L, "Bones", LocalDateTime.now().minusDays(4)),
                new Producer(4L, "Sunrise", LocalDateTime.now().minusDays(5)),
                new Producer(5L, "Studio Ghibli", LocalDateTime.now().minusDays(6))));
    }

    public Producer() {
    }

    public Producer(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static List<Producer> getProducers() {
        return producers;
    }
}
