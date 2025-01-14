package br.dev.diego.userservice.controller.response;

public record UserGetResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
