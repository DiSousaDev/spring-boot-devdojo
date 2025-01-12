package br.dev.diego.animeservice.service;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {

    private final AnimeRepository repository;

    public AnimeService(AnimeRepository repository) {
        this.repository = repository;
    }

    public List<Anime> buscar(String nome) {
        List<Anime> animes = repository.findAll();
        if (nome != null) {
            animes = repository.findByName(nome);
        }
        return animes;
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public Anime buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Anime atualizar(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
