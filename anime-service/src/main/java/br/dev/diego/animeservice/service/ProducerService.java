package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    private final ProducerRepository repository;

    public ProducerService(ProducerRepository repository) {
        this.repository = repository;
    }

    public List<Producer> buscar(String nome) {
        List<Producer> producers = repository.findAll();
        if (nome != null) {
            producers = repository.findByName(nome);
        }
        return producers;
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public Producer buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Producer atualizar(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
}
