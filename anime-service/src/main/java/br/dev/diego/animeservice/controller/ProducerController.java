package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import br.dev.diego.animeservice.service.ProducerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

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
    public ResponseEntity<ProducerResponse> salvarProducer(@RequestBody ProducerRequest producer) {
        ProducerResponse response = producerService.save(producer);
        URI location = URI.create("/v1/producers/" + response.id());

        var httpResponseHeaders = new HttpHeaders();
        httpResponseHeaders.add("Authorization", "My Key Example");

        return ResponseEntity.created(location).headers(httpResponseHeaders).body(response);
    }

}
