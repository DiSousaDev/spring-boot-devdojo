package br.dev.diego.animeservice.commons;

import br.dev.diego.animeservice.domain.Producer;

import java.time.LocalDateTime;
import java.util.List;

public class ProducertUtils {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.parse("2025-01-10T17:48:37.550866");

    public static Producer createProducer() {
        return new Producer(1L, "Bones", LOCAL_DATE_TIME);
    }

    public static List<Producer> createProducerList() {
        return List.of(
                createProducer(),
                new Producer(2L, "Madhouse", LOCAL_DATE_TIME),
                new Producer(3L, "Toei Animation Test", LOCAL_DATE_TIME),
                new Producer(4L, "Sunrise", LOCAL_DATE_TIME)
        );
    }

}
