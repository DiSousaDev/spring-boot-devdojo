package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.mappers.AnimeMapper;
import br.dev.diego.animeservice.domain.request.AnimeRequest;
import br.dev.diego.animeservice.domain.request.AnimeResponse;
import br.dev.diego.animeservice.domain.request.AnimeUpdateRequest;
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
        Anime.getAnimes().removeIf(a -> a.getId().equals(id));
    }

    private static Anime getAnimeEntity(Long id) {
        return Anime.getAnimes().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
