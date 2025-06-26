package ufv.Prueva3.AASM.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ufv.Prueva3.AASM.model.Character;
import ufv.Prueva3.AASM.model.Starship;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
import java.io.IOException;
import java.util.Date;
import com.google.gson.Gson;

@RestController
public class SwapiController {

    @GetMapping("/api/people/{id}")
    public Character getPersonById(@PathVariable("id") int id) throws IOException, InterruptedException {
        // Construir la URL a consultar en Swapi para People
        String swapiUrl = "https://swapi.info/api/people/" + id;
        // Cliente HTTP de Java 11
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(swapiUrl)).build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());  // petición síncrona
        String json = response.body();
        // Parsear JSON a objeto People usando GSON
        Character person = new Gson().fromJson(json, Character.class);
        // Establecer la fecha de consulta a ahora
        person.setDate(new Date());
        return person;  // Spring Boot lo convertirá a JSON automáticamente
    }

    @GetMapping("/api/starships/{id}")
    public Starship getStarshipById(@PathVariable("id") int id) throws IOException, InterruptedException {
        String swapiUrl = "https://swapi.info/api/starships/" + id;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(swapiUrl)).build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        Starship ship = new Gson().fromJson(response.body(), Starship.class);
        ship.setDate(new Date());
        return ship;
    }
}