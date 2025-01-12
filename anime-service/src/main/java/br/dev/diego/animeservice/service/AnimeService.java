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

    private final AnimeMapper mapper;
    private final AnimeRepository repository;

    public AnimeService(AnimeMapper mapper, AnimeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<AnimeResponse> buscar(String nome) {
        List<Anime> animes = repository.findAll();
        if (nome != null) {
            animes = repository.findByName(nome);
        }
        return mapper.toResponseList(animes);
    }

    public AnimeResponse save(AnimeRequest request) {
        Anime entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    public AnimeResponse buscarPorId(Long id) {
        Anime anime = repository.findById(id);
        return mapper.toResponse(anime);
    }

    public AnimeResponse atualizar(Long id, AnimeUpdateRequest request) {
        Anime anime = repository.findById(id);
        mapper.updateAnimeFromRequest(request, anime);
        return mapper.toResponse(anime);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
