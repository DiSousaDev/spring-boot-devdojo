package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.request.AnimeRequest;
import br.dev.diego.animeservice.domain.request.AnimeResponse;
import br.dev.diego.animeservice.domain.request.AnimeUpdateRequest;
import br.dev.diego.animeservice.service.AnimeService;
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
@RequestMapping("v1/animes")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public ResponseEntity<List<AnimeResponse>> obterAnimes(@RequestParam(required = false) String nome) {
       return ResponseEntity.ok(animeService.buscar(nome));
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeResponse> obterAnimePorId(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AnimeResponse> salvarAnime(@RequestBody AnimeRequest anime) {
        AnimeResponse response = animeService.save(anime);
        URI location = URI.create("/v1/animes/" + response.id());

        var httpResponseHeaders = new HttpHeaders();
        httpResponseHeaders.add("Authorization", "My Key Example");

        return ResponseEntity.created(location).headers(httpResponseHeaders).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<AnimeResponse> atualizarAnime(@PathVariable Long id, @RequestBody AnimeUpdateRequest request) {
        AnimeResponse animeResponse = animeService.atualizar(id, request);
        return ResponseEntity.ok(animeResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAnime(@PathVariable Long id) {
        animeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
