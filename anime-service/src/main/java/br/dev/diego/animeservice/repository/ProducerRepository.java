package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.domain.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProducerRepository {

    private static List<Producer> producers = new ArrayList<>();

    static {
        producers.addAll(Arrays.asList(
                new Producer(1L, "Toei Animation", LocalDateTime.now().minusDays(2)),
                new Producer(2L, "Madhouse", LocalDateTime.now().minusDays(3)),
                new Producer(3L, "Bones", LocalDateTime.now().minusDays(4)),
                new Producer(4L, "Sunrise", LocalDateTime.now().minusDays(5)),
                new Producer(5L, "Studio Ghibli", LocalDateTime.now().minusDays(6))));
    }

    public List<Producer> findAll() {
        return producers;
    }

    public Producer save(Producer producer) {
        findAll().add(producer);
        return producer;
    }

    public Producer findById(Long id) {
        return findAll().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    public List<Producer> findByName(String name) {
        return findAll().stream()
                .filter(producer -> producer.getName().contains(name))
                .toList();
    }

    public void deleteById(Long id) {
        findById(id);
        findAll().removeIf(a -> a.getId().equals(id));
    }

}
