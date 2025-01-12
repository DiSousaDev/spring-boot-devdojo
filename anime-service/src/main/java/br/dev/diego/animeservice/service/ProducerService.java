package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.mappers.ProducerMapper;
import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import br.dev.diego.animeservice.domain.request.ProducerUpdateRequest;
import br.dev.diego.animeservice.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    private final ProducerRepository repository;

    public ProducerService(ProducerRepository repository) {
        this.repository = repository;
    }

    public List<ProducerResponse> buscar(String nome) {
        List<Producer> producers = repository.findAll();
        if (nome != null) {
            producers = repository.findByName(nome);
        }
        return MAPPER.toResponseList(producers);
    }

    public ProducerResponse save(ProducerRequest request) {
        Producer entity = MAPPER.toEntity(request);
        return MAPPER.toResponse(repository.save(entity));
    }

    public ProducerResponse buscarPorId(Long id) {
        Producer producer = repository.findById(id);
        return MAPPER.toResponse(producer);
    }

    public ProducerResponse atualizar(Long id, ProducerUpdateRequest request) {
        Producer producer = repository.findById(id);
        MAPPER.updateProducerFromRequest(request, producer);
        return MAPPER.toResponse(producer);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
}
