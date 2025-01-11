package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    private final List<Anime> animes = new Anime().obterAnimesList();

    @GetMapping
    public List<Anime> obterAnimes(@RequestParam(required = false) String nome) {
        if (nome == null) return animes;
        return animes.stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(nome))
                .toList();
    }

    @GetMapping("{id}")
    public Anime obterAnimePorId(@PathVariable Long id) {
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
