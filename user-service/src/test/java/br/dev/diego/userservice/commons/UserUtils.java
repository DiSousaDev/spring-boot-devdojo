package br.dev.diego.userservice.commons;

import br.dev.diego.userservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {

    public List<User> newUserList() {
        var toyohisa = new User(1L, "Toyohisa", "Shimazu", "toyohisa@drifters.com");
        var ichigo = new User(2L, "Ichigo", "Kurosaki", "ichigo@bleach.com");
        var ash = new User(3L, "Ash", "Ketchum", "ash@pokemon.com");

        return new ArrayList<>(List.of(toyohisa, ichigo, ash));
    }

    public User newUserToSave() {
        return new User(99L, "Yusuke", "Urameshi", "yusuke@yuyuhakusho.com");
    }
}
