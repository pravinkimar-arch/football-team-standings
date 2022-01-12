package com.sports.footballstand.utils;

import com.sports.footballstand.models.League;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

//@Slf4j - isssue with lombok plugin in intellij version
@Service
public class FootballStandingRestUtils {

    private static final Logger log = LoggerFactory.getLogger(FootballStandingRestUtils.class);

    private static final String ACTION = "action";
    private static final String LEAGUE_ACTION = "get_leagues";

    @Value("${football.standing.base.url}")
    private String baseFootballUrl;

    @Value("${football.standing.api}")
    private String footballStandAPIKey;

    private static final String APIKEY = "APIkey";
    private final RestTemplate restTemplate;


    @Autowired
    public FootballStandingRestUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    public UriComponentsBuilder getUriComponentsBuilder(String url,
                                                         Map<String, String> queryParams) {
        queryParams.put(APIKEY, footballStandAPIKey);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        queryParams.forEach(builder::queryParam);
        return builder;
    }

    public boolean CheckHostAvailability() {
        boolean connectivity = true;
        try{
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put(ACTION, LEAGUE_ACTION);
            UriComponentsBuilder builder = getUriComponentsBuilder(baseFootballUrl, queryParams);
            List<League> availabilityCheckResult =   Arrays.asList(this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    new HttpEntity<>(getHeaders()), League[].class)
                    .getBody());
            if(availabilityCheckResult.size() < 1) {
                log.info("Dependent host is not available");
                connectivity = false;
            } else {
                log.info("Dependent host is available");
            }
        }catch(Exception e) {
            log.info("Dependent host is not available");
            connectivity = false;
        }
        return connectivity;
    }
}

