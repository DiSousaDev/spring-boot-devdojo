package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.mappers.ProducerMapper;
import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import br.dev.diego.animeservice.domain.request.ProducerUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    public List<ProducerResponse> buscarProducers(String nome) {
        List<Producer> producers = Producer.getProducers();
        if (nome != null) {
            producers = Producer.getProducers().stream()
                    .filter(producer -> producer.getName().contains(nome))
                    .toList();
        }
        return MAPPER.toResponseList(producers);
    }

    public ProducerResponse save(ProducerRequest request) {
        Producer entity = MAPPER.toEntity(request);
        Producer.getProducers().add(entity);
        return MAPPER.toResponse(entity);
    }

    public ProducerResponse buscarProducerPorId(Long id) {
        Producer producer = getProducerEntity(id);
        return MAPPER.toResponse(producer);
    }

    public ProducerResponse atualizar(Long id, ProducerUpdateRequest request) {
        Producer producer = getProducerEntity(id);
        MAPPER.updateProducerFromRequest(request, producer);
        return MAPPER.toResponse(producer);
    }

    public ResponseEntity<Void> deletar(Long id) {
        getProducerEntity(id);
        Producer.getProducers().removeIf(a -> a.getId().equals(id));
        return ResponseEntity.noContent().build();
    }

    private static Producer getProducerEntity(Long id) {
        return Producer.getProducers().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
