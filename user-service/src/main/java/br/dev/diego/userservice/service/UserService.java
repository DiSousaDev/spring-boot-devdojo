package br.dev.diego.userservice.service;

import br.dev.diego.userservice.domain.User;
import br.dev.diego.userservice.respository.UserHardCodedRepository;
import br.dev.diego.userservice.service.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserHardCodedRepository repository;

    public UserService(UserHardCodedRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll(String firstName) {
        return firstName == null ? repository.findAll() : repository.findByFirstName(firstName);
    }

    public User findByIdOrThrowNotFound(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not Found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void delete(Long id) {
        var user = findByIdOrThrowNotFound(id);
        repository.delete(user);
    }

    public void update(User userToUpdate) {
        findByIdOrThrowNotFound(userToUpdate.getId());
        repository.update(userToUpdate);
    }
}
