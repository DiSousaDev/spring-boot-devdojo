package br.dev.diego.animeservice.domain.mappers;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import br.dev.diego.animeservice.domain.request.ProducerUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    ProducerResponse toResponse(Producer producer);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(new java.util.Random().nextLong(999))")
    Producer toEntity(ProducerRequest producerRequest);

    List<ProducerResponse> toResponseList(List<Producer> producers);

    void updateProducerFromRequest(ProducerUpdateRequest request, @MappingTarget Producer producer);

}
