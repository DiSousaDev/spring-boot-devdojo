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

    private final ProducerMapper mapper;
    private final ProducerRepository repository;

    public ProducerService(ProducerMapper mapper, ProducerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<ProducerResponse> buscar(String nome) {
        List<Producer> producers = repository.findAll();
        if (nome != null) {
            producers = repository.findByName(nome);
        }
        return mapper.toResponseList(producers);
    }

    public ProducerResponse save(ProducerRequest request) {
        Producer entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public ProducerResponse buscarPorId(Long id) {
        Producer producer = repository.findById(id);
        return mapper.toResponse(producer);
    }

    public ProducerResponse atualizar(Long id, ProducerUpdateRequest request) {
        Producer producer = repository.findById(id);
        mapper.updateProducerFromRequest(request, producer);
        return mapper.toResponse(producer);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
}
