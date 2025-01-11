package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.ProducerRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("v1/producers")
public class ProducerController {

    @GetMapping
    public List<Producer> obterProducers(@RequestParam(required = false) String nome) {
        if (nome == null) return Producer.getProducers();
        return Producer.getProducers().stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(nome))
                .toList();
    }

    @GetMapping("{id}")
    public Producer obterProducerPorId(@PathVariable Long id) {
        return Producer.getProducers().stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @PostMapping
    public Producer salvarProducer(@RequestBody ProducerRequest producer) {
        Long id = new Random().nextLong(999);
        Producer producerSaved = new Producer(id, producer.getName());
        Producer.getProducers().add(producerSaved);
        return producerSaved;
    }

}
