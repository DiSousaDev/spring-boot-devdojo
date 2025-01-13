package br.dev.diego.animeservice.controller;

import external.dependency.Connection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/connections")
public class ConnectionController {

    private final Connection connection;

    public ConnectionController(Connection connection) {
        this.connection = connection;
    }

    @GetMapping
    public ResponseEntity<Connection> getConnection() {
        return ResponseEntity.ok(connection);
    }

}
