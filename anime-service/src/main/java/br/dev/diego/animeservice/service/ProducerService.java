package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.mappers.ProducerMapper;
import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    public ProducerResponse save(ProducerRequest request) {
        Producer entity = MAPPER.toEntity(request);
        Producer.getProducers().add(entity);
        return MAPPER.toResponse(entity);
    }

}
