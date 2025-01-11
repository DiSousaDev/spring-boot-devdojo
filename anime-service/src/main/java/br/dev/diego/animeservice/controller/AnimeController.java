package br.dev.diego.animeservice.controller;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.request.AnimeRequest;
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
@RequestMapping("v1/animes")
public class AnimeController {

    @GetMapping
    public List<Anime> obterAnimes(@RequestParam(required = false) String nome) {
        if (nome == null) return Anime.getAnimes();
        return Anime.getAnimes().stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(nome))
                .toList();
    }

    @GetMapping("{id}")
    public Anime obterAnimePorId(@PathVariable Long id) {
        return Anime.getAnimes().stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @PostMapping
    public Anime salvarAnime(@RequestBody AnimeRequest anime) {
        Long id = new Random().nextLong(999);
        Anime animeSaved = new Anime(id, anime.getName());
        Anime.getAnimes().add(animeSaved);
        return animeSaved;
    }

}
