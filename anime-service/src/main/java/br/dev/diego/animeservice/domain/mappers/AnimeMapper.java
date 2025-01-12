package br.dev.diego.animeservice.domain.mappers;

import br.dev.diego.animeservice.domain.Anime;
import br.dev.diego.animeservice.domain.request.AnimeRequest;
import br.dev.diego.animeservice.domain.request.AnimeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnimeMapper {

    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    AnimeResponse toResponse(Anime anime);

    @Mapping(target = "id", expression = "java(new java.util.Random().nextLong(999))")
    Anime toEntity(AnimeRequest animeRequest);

    List<AnimeResponse> toResponseList(List<Anime> animes);


}
