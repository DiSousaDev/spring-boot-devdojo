package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.mappers.AnimeMapper;
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
    private final AnimeMapper mapper;

    public AnimeController(AnimeService animeService, AnimeMapper mapper) {
        this.animeService = animeService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<AnimeResponse>> obterAnimes(@RequestParam(required = false) String nome) {
        return ResponseEntity.ok(mapper.toResponseList(animeService.buscar(nome)));
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeResponse> obterAnimePorId(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(animeService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<AnimeResponse> salvarAnime(@RequestBody AnimeRequest request) {
        Anime entity = mapper.toEntity(request);
        AnimeResponse response = mapper.toResponse(animeService.save(entity));
        URI location = URI.create("/v1/animes/" + response.id());

        var httpResponseHeaders = new HttpHeaders();
        httpResponseHeaders.add("Authorization", "My Key Example");

        return ResponseEntity.created(location).headers(httpResponseHeaders).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<AnimeResponse> atualizarAnime(@PathVariable Long id, @RequestBody AnimeUpdateRequest request) {
        Anime anime = animeService.atualizar(id);
        mapper.updateAnimeFromRequest(request, anime);
        return ResponseEntity.ok(mapper.toResponse(anime));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAnime(@PathVariable Long id) {
        animeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
