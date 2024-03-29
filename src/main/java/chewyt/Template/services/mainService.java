package chewyt.Template.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import chewyt.Template.models.*;
import chewyt.Template.repositories.*;
import static chewyt.Template.Constants.*;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class mainService {

    @Autowired
    standardRepo repo;

    Logger logger = Logger.getLogger(mainService.class.getName());

    public List<Hero> getHero(String hero) {

        logger.info(ENV_MARVELAPIKEY_PUBLIC.toString());
        logger.info(ENV_MARVELAPIKEY_HASH.toString());

        String url = UriComponentsBuilder
                .fromUriString(URL_MARVEL)
                .queryParam("nameStartsWith", hero.replace(" ", "+"))
                .queryParam("ts", "1")
                .queryParam("apikey", ENV_MARVELAPIKEY_PUBLIC)
                .queryParam("hash", ENV_MARVELAPIKEY_HASH)
                .toUriString();

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        logger.log(Level.INFO, resp.getStatusCode().toString());
        logger.log(Level.INFO, resp.getHeaders().toString());
        // logger.log(Level.INFO, resp.getBody().toString());

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            final JsonArray searchresults = result.getJsonObject("data").getJsonArray("results");
            return searchresults.stream()
                    .map(v -> (JsonObject) v)
                    .map(Hero::create)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.severe("GG: %s".formatted(e.getMessage()));
        }

        return null;
    }

}
