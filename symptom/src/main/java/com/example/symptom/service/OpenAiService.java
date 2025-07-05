package com.example.symptom.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    public String analyzeSymptom(String symptom) throws IOException, InterruptedException {
        String prompt = "Given the following symptom: \"" + symptom +
                "\". Return the most relevant medical department like Cardiology, Dermatology, etc.";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openAiApiKey)
                .POST(HttpRequest.BodyPublishers.ofString("""
            {
              "model": "gpt-3.5-turbo",
              "messages": [
                {"role": "system", "content": "You are a medical assistant who helps identify which department a patient should visit."},
                {"role": "user", "content": "%s"}
              ]
            }
            """.formatted(prompt)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body())
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
                .trim();
    }

}

