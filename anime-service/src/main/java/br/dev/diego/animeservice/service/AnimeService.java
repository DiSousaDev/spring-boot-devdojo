package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.mappers.AnimeMapper;
import br.dev.diego.animeservice.domain.request.AnimeRequest;
import br.dev.diego.animeservice.domain.request.AnimeResponse;
import br.dev.diego.animeservice.domain.request.AnimeUpdateRequest;
import br.dev.diego.animeservice.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {

    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    private final AnimeRepository repository;

    public AnimeService(AnimeRepository repository) {
        this.repository = repository;
    }

    public List<AnimeResponse> buscarAnimes(String nome) {
        List<Anime> animes = repository.getAnimes();
        if (nome != null) {
            animes = repository.getAnimes().stream()
                    .filter(anime -> anime.getName().contains(nome))
                    .toList();
        }
        return MAPPER.toResponseList(animes);
    }

    public AnimeResponse save(AnimeRequest request) {
        Anime entity = MAPPER.toEntity(request);
        repository.getAnimes().add(entity);
        return MAPPER.toResponse(entity);
    }

    public AnimeResponse buscarAnimePorId(Long id) {
        Anime anime = getAnimeEntity(id);
        return MAPPER.toResponse(anime);
    }

    public AnimeResponse atualizar(Long id, AnimeUpdateRequest request) {
        Anime anime = getAnimeEntity(id);
        MAPPER.updateAnimeFromRequest(request, anime);
        return MAPPER.toResponse(anime);
    }


    public void deletar(Long id) {
        getAnimeEntity(id);
        repository.getAnimes().removeIf(a -> a.getId().equals(id));
    }

    private Anime getAnimeEntity(Long id) {
        return repository.getAnimes().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
