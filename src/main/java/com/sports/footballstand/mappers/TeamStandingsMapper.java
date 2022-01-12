package com.sports.footballstand.mappers;

import com.sports.footballstand.dtos.FootballTeamStandingDto;
import com.sports.footballstand.models.TeamStandingDetails;
import org.springframework.stereotype.Component;

@Component
public class TeamStandingsMapper {

    public FootballTeamStandingDto toDto(TeamStandingDetails teamStandingDetails, String countryId){
        FootballTeamStandingDto dto = new FootballTeamStandingDto();
        dto.setCountry("(" + countryId + ") - " + teamStandingDetails.getCountryName());
        dto.setLeague("(" + teamStandingDetails.getLeagueId() + ") - " + teamStandingDetails.getLeagueName());
        dto.setTeam("(" + teamStandingDetails.getTeamId() + ") - " + teamStandingDetails.getTeamName());
        dto.setOverallPosition(teamStandingDetails.getOverAllPosition());
        return dto;
    }
}

