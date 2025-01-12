package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.mappers.AnimeMapper;
import br.dev.diego.animeservice.domain.request.AnimeRequest;
import br.dev.diego.animeservice.domain.request.AnimeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    public List<AnimeResponse> buscarAnimes(String nome) {
        List<Anime> animes = Anime.getAnimes();
        if (nome != null) {
            animes = Anime.getAnimes().stream()
                    .filter(anime -> anime.getName().contains(nome))
                    .toList();
        }
        return MAPPER.toResponseList(animes);
    }

    public AnimeResponse save(AnimeRequest request) {
        Anime entity = MAPPER.toEntity(request);
        Anime.getAnimes().add(entity);
        return MAPPER.toResponse(entity);
    }

    public ResponseEntity<AnimeResponse> buscarAnimePorId(Long id) {
        Anime anime = Anime.getAnimes().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow();
        return ResponseEntity.ok(MAPPER.toResponse(anime));
    }

}
