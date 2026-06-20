package dev.melissamorgan.splatoonweapons.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamRecommenderRequest;
import dev.melissamorgan.splatoonweapons.entities.recommenderTools.TeamRecommenderResponse;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

@Component
public class LambdaPredictionClient {

    private final ObjectMapper objectMapper;
    private static final String RECOMMEND_URL = "http://18.223.117.75:8000/recommend";

    public LambdaPredictionClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public TeamRecommenderResponse fetchPrediction(TeamRecommenderRequest request) {
        try {
            // 1. Serialize the request exactly as it is
            String jsonPayload = objectMapper.writeValueAsString(request);
            System.out.println("JSON Payload: " + jsonPayload);

            // 2. Build and send the HTTP POST request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(RECOMMEND_URL))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Lambda returned error code: " + response.statusCode());
            }

            // 3. Deserialize the Python JSON straight into your Java Response object
            return objectMapper.readValue(response.body(), TeamRecommenderResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            // In a real app, you'd handle this more gracefully, but this gets us started
            return new TeamRecommenderResponse();
        }
    }
}
