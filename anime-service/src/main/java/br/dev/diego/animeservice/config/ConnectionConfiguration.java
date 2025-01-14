package br.dev.diego.animeservice.config;

import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    private final ConnectionProperties properties;

    public ConnectionConfiguration(ConnectionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Connection connection() {
        return new Connection(properties.url(), properties.username(), properties.password());
    }
}
