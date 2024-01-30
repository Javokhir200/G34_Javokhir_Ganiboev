package org.example.service.impl;

import com.google.gson.Gson;
import org.example.domain.Response;
import org.example.service.ApiService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class ApiServiceImpl implements ApiService {
    private ApiServiceImpl(){

    }
    private static ApiServiceImpl apiService;
    public static ApiServiceImpl getApiService(){
        if (Objects.isNull(apiService)){
            apiService = new ApiServiceImpl();
            return apiService;
        }
        return apiService;
    }
    @Override
    public String sendFact(Integer monthNum, Integer dayNum) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format("https://numbersapi.p.rapidapi.com/%d/%d/date?fragment=true&json=true",
                        monthNum,
                        dayNum)))
                .header("X-RapidAPI-Key", "7da6db23c1msh25b0e58839d184ap1bdeeajsn8a394d777aec")
                .header("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        Response resp = gson.fromJson(response.body(),Response.class);
        return  resp.getText();
    }
}
