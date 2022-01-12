package com.sports.footballstand.services;

import com.sports.footballstand.exceptions.BusinessException;
import com.sports.footballstand.mappers.TeamStandingsMapper;
import com.sports.footballstand.models.TeamStandingDetails;
import com.sports.footballstand.utils.FootballStandingRestUtils;
import lombok.extern.slf4j.Slf4j;
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
public class TeamStandingsOperations implements IOperations{

    private static final Logger log = LoggerFactory.getLogger(TeamStandingsOperations.class);

    private static final String ACTION = "action";
    private static final String LEAGUE_ACTION = "get_leagues";
    private static final String STANDINGS_ACTION = "get_standings";

    private final FootballStandingRestUtils footballStandingRestUtils;
    private final TeamStandingsMapper teamStandingsMapper;
    private final RestTemplate restTemplate;

    @Value("${football.standing.base.url}")
    private String baseFootballUrl;

    @Autowired
    public TeamStandingsOperations(FootballStandingRestUtils footballStandingRestUtils,
                                    TeamStandingsMapper teamStandingsMapper,
                                    RestTemplate restTemplate) {

        this.footballStandingRestUtils = footballStandingRestUtils;
        this.teamStandingsMapper = teamStandingsMapper;
        this.restTemplate = restTemplate;
    }

    @Cacheable("Football")
    @Override
    public List<TeamStandingDetails> fetch(String leagueId){
        log.info("Making network call to server to fetch team standings, leagueId=" + leagueId);
        try {
            HashMap<String, String> queryParams = new HashMap<>();
            queryParams.put(ACTION, STANDINGS_ACTION);
            queryParams.put("league_id", leagueId);
            UriComponentsBuilder builder = footballStandingRestUtils.getUriComponentsBuilder(baseFootballUrl, queryParams);
            return Arrays.asList(this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    new HttpEntity<>(footballStandingRestUtils.getHeaders()), TeamStandingDetails[].class)
                    .getBody());
        }catch (Exception e) {
            String errorCode = "602";
            String message = "Exception in service layer, while connecting football API server " + e.getMessage();
            log.error(MessageFormat.format("Bad Request: ErrorCode={0}, Message={1}.",errorCode, message));
            throw new BusinessException(errorCode, message);
        }
    }

    public TeamStandingDetails filter(List<TeamStandingDetails> standings, String teamName){
        if(teamName.isEmpty()) {
            String errorCode = "603";
            String message = "League Name is blank, please send a valid league name ";
            log.error(MessageFormat.format("Bad Request : ErrorCode={0}, Message={1}.",errorCode, message));
            throw new BusinessException(errorCode, message);
        }
        return standings.stream()
                .filter(standing -> standing.getTeamName().equalsIgnoreCase(teamName)).findFirst().orElse(null);
    }
}
