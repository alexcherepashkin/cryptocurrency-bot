package com.knubi.bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoApiService implements ApiSource {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Value("${cryptocurrency-api.url}")
    private String url;


    @Override
    public Optional<String> getCurrencyData() {
        String json = retrieveData(url);

        try {
//            JsonNode[] nodes = mapper.readValue(json, JsonNode[].class);

//            for (JsonNode obj : nodes) {
//                todo: mapper.convertValue()
//            }
        } catch (Exception e) {
            log.error("Failed to deserialize content: " + json, e);
        }

        return Optional.ofNullable(json);
    }

    private String retrieveData(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode() == HttpStatus.OK ? response.getBody() : EMPTY;
    }
}
