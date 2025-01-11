package br.dev.diego.animeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("v1/heroes")
public class HeroController {

    private final Logger log = LoggerFactory.getLogger(HeroController.class);

    @GetMapping
    public List<String> obterAnimes() {
        log.info(Thread.currentThread().getName());
        return Arrays.asList("Guts", "Zoro", "Kakashi", "Goku");
    }

    @GetMapping("filter")
    public List<String> obterAnime(@RequestParam(required = false) String nome) {
        log.info(Thread.currentThread().getName());
        return Stream.of("Guts", "Zoro", "Kakashi", "Goku").filter(hero -> hero.equalsIgnoreCase(nome)).toList();
    }

    @GetMapping("filterList")
    public List<String> obterAnimesFiltrado(@RequestParam(defaultValue = "") List<String> nomes) {
        log.info(Thread.currentThread().getName());
        return Stream.of("Guts", "Zoro", "Kakashi", "Goku").filter(nomes::contains).toList();
    }

    @GetMapping("{nome}")
    public String obterAnimePorNome(@PathVariable String nome) {
        log.info(Thread.currentThread().getName());
        return Stream.of("Guts", "Zoro", "Kakashi", "Goku")
                .filter(hero -> hero.equalsIgnoreCase(nome))
                .findFirst().orElse("Hero not found");
    }

}
