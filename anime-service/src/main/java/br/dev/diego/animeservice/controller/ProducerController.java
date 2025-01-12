package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.mappers.ProducerMapper;
import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import br.dev.diego.animeservice.domain.request.ProducerUpdateRequest;
import br.dev.diego.animeservice.service.ProducerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final ProducerMapper mapper;

    public ProducerController(ProducerService producerService, ProducerMapper mapper) {
        this.producerService = producerService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ProducerResponse>> obterProducers(@RequestParam(required = false) String nome) {
        return ResponseEntity.ok(mapper.toResponseList(producerService.buscar(nome)));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerResponse> obterProducerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(producerService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<ProducerResponse> salvarProducer(@RequestBody ProducerRequest request) {
        Producer entity = mapper.toEntity(request);
        ProducerResponse response = mapper.toResponse(producerService.save(entity));
        URI location = URI.create("/v1/producers/" + response.id());

        var httpResponseHeaders = new HttpHeaders();
        httpResponseHeaders.add("Authorization", "My Key Example");

        return ResponseEntity.created(location).headers(httpResponseHeaders).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProducerResponse> atualizar(@PathVariable Long id, @RequestBody ProducerUpdateRequest request) {
        Producer producer = producerService.atualizar(id);
        mapper.updateProducerFromRequest(request, producer);
        return ResponseEntity.ok(mapper.toResponse(producer));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarProducer(@PathVariable Long id) {
        producerService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
