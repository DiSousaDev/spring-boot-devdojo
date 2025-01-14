package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.domain.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProducerData {

    private final List<Producer> producers = new ArrayList<>();

    {
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
