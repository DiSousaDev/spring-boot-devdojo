package br.dev.diego.userservice.respository;

import br.dev.diego.userservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserData {

    private final List<User> users = new ArrayList<>(3);

    {
        var goku = new User(1L, "Goku", "Son", "goku.son@dbz.com");
        var gohan = new User(2L, "Gohan", "Son", "gohan.son@dbz.com");
        var goten = new User(3L, "Goten", "Son", "goten.son@dbz.com");
        users.addAll(List.of(goku, gohan, goten));
    }

    public List<User> getUsers() {
        return users;
    }
}
