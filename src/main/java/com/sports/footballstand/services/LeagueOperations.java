package com.sports.footballstand.services;

import com.sports.footballstand.exceptions.BusinessException;
import com.sports.footballstand.models.League;
import com.sports.footballstand.utils.FootballStandingRestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//@Slf4j - isssue with lombok plugin in intellij version
@Service
public class LeagueOperations implements IOperations{

    private static final Logger log = LoggerFactory.getLogger(TeamStandingsOperations.class);
    private static final String ACTION = "action";
    private static final String LEAGUE_ACTION = "get_leagues";

    private final FootballStandingRestUtils footballStandingRestUtils;
    private final RestTemplate restTemplate;

    @Value("${football.standing.base.url}")
    private String baseFootballUrl;

    @Autowired
    public LeagueOperations(FootballStandingRestUtils footballStandingRestUtils,
                                    RestTemplate restTemplate) {
        this.footballStandingRestUtils = footballStandingRestUtils;
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable("Football")
    public List<League> fetch(String leagueName){
        log.info("Making network call to server to fetch leagues, leagueName=" + leagueName);
        try {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put(ACTION, LEAGUE_ACTION);
            UriComponentsBuilder builder = footballStandingRestUtils.getUriComponentsBuilder(baseFootballUrl, queryParams);
            return Arrays.asList(this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    new HttpEntity<>(footballStandingRestUtils.getHeaders()), League[].class)
                    .getBody());
        }catch (Exception e) {
            String errorCode = "602";
            String message = "Exception in service layer, while connecting football API server " + e.getMessage();
            log.error(MessageFormat.format("Bad Request: ErrorCode={0}, Message={1}.",errorCode, message));
            throw new BusinessException(errorCode, message);
        }
    }

    public League filter(List<League> leagues, String leagueName){
        if(leagueName.isEmpty()) {
            String errorCode = "603";
            String message = "League Name is blank, please send a valid league name ";
            log.error(MessageFormat.format("Bad Request: ErrorCode={0}, Message={1}.",errorCode, message));
            throw new BusinessException(errorCode, message);
        }
        return  leagues.stream()
                .filter(league -> league.getLeagueName().equalsIgnoreCase(leagueName)).findFirst().orElse(null);
    }
}