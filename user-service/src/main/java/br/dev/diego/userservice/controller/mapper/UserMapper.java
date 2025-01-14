package br.dev.diego.userservice.controller.mapper;

import br.dev.diego.userservice.controller.request.UserPostRequest;
import br.dev.diego.userservice.controller.request.UserPutRequest;
import br.dev.diego.userservice.controller.response.UserGetResponse;
import br.dev.diego.userservice.controller.response.UserPostResponse;
import br.dev.diego.userservice.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    User toUser(UserPostRequest postRequest);

    User toUser(UserPutRequest request);

    UserPostResponse toUserPostResponse(User user);

    UserGetResponse toUserGetResponse(User user);

    List<UserGetResponse> toUserGetResponseList(List<User> users);

}
