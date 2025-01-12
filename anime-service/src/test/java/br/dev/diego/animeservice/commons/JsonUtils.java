package br.dev.diego.animeservice.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    @Autowired
    private ResourceLoader resourceLoader;

    public String loadJson(String fileName) {
        try {
            return new String(resourceLoader.getResource("classpath:" + fileName).getInputStream().readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
