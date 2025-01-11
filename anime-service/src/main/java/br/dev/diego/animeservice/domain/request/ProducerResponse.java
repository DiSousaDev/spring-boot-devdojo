package br.dev.diego.animeservice.domain.request;

import java.time.LocalDateTime;

public record ProducerResponse(
        Long id,
        String name,
        LocalDateTime createdAt
) {

}
