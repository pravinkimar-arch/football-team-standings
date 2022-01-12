package com.sports.footballstand.services;

import com.sports.footballstand.models.League;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Slf4j - isssue with lombok plugin in intellij version
@Service
public class PopulateCache {

    private static final Logger log = LoggerFactory.getLogger(PopulateCache.class);

    public static final String defaultLeageName = "default";

    private TeamStandingsOperations teamStandingsOperations;
    private LeagueOperations leagueOperations;


    @Autowired
    PopulateCache(TeamStandingsOperations teamStandingsOperations,
                  LeagueOperations leagueOperations){
        this.leagueOperations = leagueOperations;
        this.teamStandingsOperations = teamStandingsOperations;
    }

    public void populate(){
        List<League> leagues = leagueOperations.fetch(defaultLeageName);
        leagues.forEach(league -> leagueOperations.fetch(league.getLeagueName()));
        leagues.forEach(league -> {teamStandingsOperations.fetch(league.getLeagueId()); });
        log.info("Cache Populated" );
    }
}
