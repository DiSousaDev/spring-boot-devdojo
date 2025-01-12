package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.domain.Producer;
import org.springframework.stereotype.Repository;

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

    public List<Producer> getProducers() {
        return producers;
    }

}
