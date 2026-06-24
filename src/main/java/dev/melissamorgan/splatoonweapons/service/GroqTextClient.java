package dev.melissamorgan.splatoonweapons.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamExplainerRequest;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamExplainerResponse;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

@Component
public class GroqTextClient {

    private final ObjectMapper objectMapper;
    private static final String RAG_URL = "";

    public GroqTextClient(ObjectMapper objectMapper) {this.objectMapper = objectMapper;}

    public TeamExplainerResponse fetchExplanation(TeamExplainerRequest request) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(request);
            System.out.println("Fetching explanation: " + jsonPayload);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(RAG_URL))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Explainer returned error code: " + response.statusCode());
            }

            return objectMapper.readValue(response.body(), TeamExplainerResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new TeamExplainerResponse();
        }
    }
}
