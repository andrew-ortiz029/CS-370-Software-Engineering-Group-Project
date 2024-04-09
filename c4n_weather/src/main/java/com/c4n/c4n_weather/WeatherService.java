package com.c4n.c4n_weather;
import com.c4n.c4n_weather.Locations.Weather;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;



@Service
public class WeatherService {
    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/3.0/onecall").build();
    }

    public Mono<Weather> getWeatherData(String lat, String lon, String apiKey) {
        String url = "?lat=" + lat + "&lon=" + lon + "&exclude=" + "&appid=ee0669cae800f45f75666b998e44ec77";
        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(Weather.class);
    }
}
