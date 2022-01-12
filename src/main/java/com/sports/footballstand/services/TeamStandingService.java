package com.sports.footballstand.services;

import com.sports.footballstand.dtos.FootballTeamStandingDto;
import com.sports.footballstand.exceptions.BusinessException;
import com.sports.footballstand.mappers.TeamStandingsMapper;
import com.sports.footballstand.models.FootballStandingRequest;
import com.sports.footballstand.models.League;
import com.sports.footballstand.models.TeamStandingDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.List;

//@Slf4j - isssue with lombok plugin in intellij version
@Service
@Cacheable("Football")
public class TeamStandingService {

    private static final Logger log = LoggerFactory.getLogger(TeamStandingsOperations.class);
    private TeamStandingsOperations teamStandingsOperations;
    private LeagueOperations leagueOperations;
    private TeamStandingsMapper teamStandingsMapper;

    @Autowired
    TeamStandingService(TeamStandingsOperations teamStandingsOperations,
                        LeagueOperations leagueOperations,
                        TeamStandingsMapper teamStandingsMapper){
        this.leagueOperations = leagueOperations;
        this.teamStandingsOperations = teamStandingsOperations;
        this.teamStandingsMapper = teamStandingsMapper;
    }

    public FootballTeamStandingDto fetchTeamStanding(FootballStandingRequest footballStandingRequest) {
        try {
            List<League> leagues = leagueOperations.fetch(footballStandingRequest.getLeagueName());
            League league = leagueOperations.filter(leagues, footballStandingRequest.getLeagueName());
            if (league.getLeagueName().isEmpty()) {
                String errorCode = "601";
                String message = "leagueName is  not found ";
                log.error(MessageFormat.format("Bad Request : ErrorCode={0}, Message={1}.",errorCode, message));
                throw new BusinessException(errorCode, message);
            }
            List<TeamStandingDetails> standings = teamStandingsOperations.fetch(league.getLeagueId());
            TeamStandingDetails standingDetails = teamStandingsOperations.filter(standings, footballStandingRequest.getTeamName());
            if (standingDetails.getLeagueName().isEmpty()) {
                String errorCode = "601";
                String message = "teamName is  not found ";
                log.error(MessageFormat.format("Bad Request : ErrorCode={0}, Message={1}.",errorCode, message));
                throw new BusinessException(errorCode, message);
            }
            return teamStandingsMapper.toDto(standingDetails, league.getCountryId());
        } catch (NullPointerException e) {
            String errorCode = "601";
            String message = "leagueName or teamName not found " + e.getMessage();
            log.error(MessageFormat.format("Bad Request : ErrorCode={0}, Message={1}.",errorCode, message));
            throw new BusinessException(errorCode, message);
        } catch (Exception e) {
            String errorCode = "602";
            String message = "Exception in service layer " + e.getMessage();
            log.error(MessageFormat.format("Bad Request : ErrorCode={0}, Message={1}.",errorCode, message));
            throw new BusinessException(errorCode, message);
        }
    }
}

