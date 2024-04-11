package com.c4n.c4n_weather;
import com.c4n.c4n_weather.Locations.Weather;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;

@Service
public class WeatherService {
    private final WebClient webClient;

    //@Value("${apiKey}")
    private String apiKey = "dm32oinwefkelwfew"; //this needs to be updated with the actual api key

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/3.0/onecall").build();
    }

    public Mono<Weather> getWeatherData(String lat, String lon) {
        String url = "?lat=" + lat + "&lon=" + lon + "&exclude=" + apiKey;
        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(Weather.class);
    }
}
