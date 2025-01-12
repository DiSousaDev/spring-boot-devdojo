package br.dev.diego.animeservice.domain.mappers;

import br.dev.diego.animeservice.domain.Producer;
import br.dev.diego.animeservice.domain.request.ProducerRequest;
import br.dev.diego.animeservice.domain.request.ProducerResponse;
import br.dev.diego.animeservice.domain.request.ProducerUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProducerMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(new java.util.Random().nextLong(999))")
    Producer toEntity(ProducerRequest producerRequest);

    ProducerResponse toResponse(Producer producer);

    List<ProducerResponse> toResponseList(List<Producer> producers);

    void updateProducerFromRequest(ProducerUpdateRequest request, @MappingTarget Producer producer);

}
