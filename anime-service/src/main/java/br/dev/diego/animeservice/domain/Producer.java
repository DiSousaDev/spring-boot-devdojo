package br.dev.diego.animeservice.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Producer {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Producer producer)) return false;
        return Objects.equals(id, producer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
