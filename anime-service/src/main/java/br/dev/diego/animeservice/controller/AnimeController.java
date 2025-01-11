package br.dev.diego.animeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("v1/animes")
public class AnimeController {

    private final Logger log = LoggerFactory.getLogger(AnimeController.class);

    @GetMapping
    public List<String> obterAnimes() throws InterruptedException {
        log.info(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(1);
        return Arrays.asList("Naruto", "One Piece", "Attack on Titan", "My Hero Academia", "Demon Slayer");
    }

}
