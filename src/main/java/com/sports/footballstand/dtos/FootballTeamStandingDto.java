package com.sports.footballstand.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

//@Data
////@Getter
////@Setter
////@AllArgsConstructor
////@NoArgsConstructor
////Issue with lombak plugin for IDE

public class FootballTeamStandingDto {

    private String country;
    private String league;
    private String team;
    private int overallPosition;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setOverallPosition(int overallPosition) {
        this.overallPosition = overallPosition;
    }

    public String getCountry() {
        return country;
    }

    public String getLeague() {
        return league;
    }

    public String getTeam() {
        return team;
    }

    public int getOverallPosition() {
        return overallPosition;
    }
}
