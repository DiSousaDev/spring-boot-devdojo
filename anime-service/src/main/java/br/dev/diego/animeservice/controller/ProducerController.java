package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import br.dev.diego.animeservice.service.ProducerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<List<ProducerResponse>> obterProducers(@RequestParam(required = false) String nome) {
       return ResponseEntity.ok(producerService.buscarProducers(nome));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerResponse> obterProducerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(producerService.buscarProducerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProducerResponse> salvarProducer(@RequestBody ProducerRequest producer) {
        ProducerResponse response = producerService.save(producer);
        URI location = URI.create("/v1/producers/" + response.id());

        var httpResponseHeaders = new HttpHeaders();
        httpResponseHeaders.add("Authorization", "My Key Example");

        return ResponseEntity.created(location).headers(httpResponseHeaders).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarProducer(@PathVariable Long id) {
        return producerService.deletar(id);
    }

}
