package br.dev.diego.animeservice.repository;

import br.dev.diego.animeservice.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public class AnimeRepository {

    private final AnimeData animeData;

    public AnimeRepository(AnimeData animeData) {
        this.animeData = animeData;
    }

    public List<Anime> findAll() {
        return animeData.getAnimes();
    }

    public Anime save(Anime anime) {
        findAll().add(anime);
        return anime;
    }

    public Anime findById(Long id) {
        return findAll().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public List<Anime> findByName(String name) {
        return findAll().stream()
                .filter(anime -> anime.getName().contains(name))
                .toList();
    }

    public void deleteById(Long id) {
        findById(id);
        findAll().removeIf(a -> a.getId().equals(id));
    }
}
