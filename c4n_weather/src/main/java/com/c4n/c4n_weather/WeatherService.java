package com.c4n.c4n_weather;
import com.c4n.c4n_weather.Locations.Weather;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://api.openweathermap.org/data/3.0/onecall";

    @Value("${weather.api.key}")
    private String apiKey;

    public Weather getWeatherData(String lat, String lon) {
        String url = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;
        return restTemplate.getForObject(url, Weather.class);
    }


}