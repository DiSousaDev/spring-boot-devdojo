package br.dev.diego.animeservice.domain.mappers;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.request.AnimeRequest;
import br.dev.diego.animeservice.domain.request.AnimeResponse;
import br.dev.diego.animeservice.domain.request.AnimeUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AnimeMapper {

    @Mapping(target = "id", expression = "java(new java.util.Random().nextLong(999))")
    Anime toEntity(AnimeRequest animeRequest);

    AnimeResponse toResponse(Anime anime);

    List<AnimeResponse> toResponseList(List<Anime> animes);

    void updateAnimeFromRequest(AnimeUpdateRequest request, @MappingTarget Anime anime);

}
