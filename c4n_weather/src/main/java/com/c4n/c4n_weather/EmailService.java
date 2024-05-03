package com.c4n.c4n_weather;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Service
public class EmailService {

    @Value("${email.api.key}")
    private String emailApiKey;

    public ResponseEntity<String> sendSimpleMessage(String name, String email, String code) {
        RestTemplate restTemplate = new RestTemplate();
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("api", emailApiKey);
    
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("from", "Mailgun Sandbox <postmaster@sandboxa8746446fb92476499c507561af95ab6.mailgun.org>");
        map.add("to", name + " <" + email + ">");
        map.add("subject", "Your password reset code");
        map.add("text", "Hello " + name + ", here is the password reset code you requested: " + code + ". If you did not request a password reset, please ignore this email.");
    
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    
        return restTemplate.postForEntity("https://api.mailgun.net/v3/sandboxa8746446fb92476499c507561af95ab6.mailgun.org/messages", request, String.class);
    }

}