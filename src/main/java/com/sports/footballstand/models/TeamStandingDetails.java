package com.sports.footballstand.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

//@Data
////@Getter
////@Setter
////@AllArgsConstructor
////@NoArgsConstructor
////Issue with lombak plugin for IDE
public class TeamStandingDetails {

    private int countryId;

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("league_name")
    private String leagueName;

    @JsonProperty("league_id")
    private int leagueId;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("team_id")
    private int teamId;

    @JsonProperty("overall_league_position")
    private int overAllPosition;

    public String getTeamName() {
        return teamName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getOverAllPosition() {
        return overAllPosition;
    }
}
