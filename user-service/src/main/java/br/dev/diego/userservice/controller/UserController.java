package br.dev.diego.userservice.controller;

import br.dev.diego.userservice.controller.mapper.UserMapper;
import br.dev.diego.userservice.controller.request.UserPostRequest;
import br.dev.diego.userservice.controller.request.UserPutRequest;
import br.dev.diego.userservice.controller.response.UserGetResponse;
import br.dev.diego.userservice.controller.response.UserPostResponse;
import br.dev.diego.userservice.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;
    private final UserMapper mapper;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> findAll(@RequestParam(required = false) String firstName) {
        log.debug("Request received to list all users, param first name '{}'", firstName);

        var users = service.findAll(firstName);

        var userGetResponses = mapper.toUserGetResponseList(users);

        return ResponseEntity.ok(userGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find user by id: {}", id);

        var user = service.findByIdOrThrowNotFound(id);

        var userGetResponse = mapper.toUserGetResponse(user);

        return ResponseEntity.ok(userGetResponse);
    }

    @PostMapping
    public ResponseEntity<UserPostResponse> save(@RequestBody @Valid UserPostRequest request) {
        log.debug("Request to save user : {}", request);

        var user = mapper.toUser(request);

        var userSaved = service.save(user);

        var userPostResponse = mapper.toUserPostResponse(userSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(userPostResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete user by id: {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UserPutRequest request) {
        log.debug("Request to update user {}", request);

        var userToUpdate = mapper.toUser(request);

        service.update(userToUpdate);

        return ResponseEntity.noContent().build();
    }
}
