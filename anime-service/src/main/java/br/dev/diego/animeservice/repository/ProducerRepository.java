package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.domain.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public class ProducerRepository {

    private final ProducterData producterData;

    public ProducerRepository(ProducterData producterData) {
        this.producterData = producterData;
    }

    public List<Producer> findAll() {
        return producterData.getProducers();
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
