package com.sports.footballstand.models;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@Data
////@Getter
////@Setter
////@AllArgsConstructor
////@NoArgsConstructor
////Issue with lombak plugin for IDE
public class FootballStandingRequest {

    @NotBlank(message = "Country name can not be blank")
    private String countryName;

    @NotBlank(message = "League name can not be blank")
    private String leagueName;

    @NotBlank(message = "Team name can not be blank")
    private String teamName;

    public String getLeagueName() {
        return leagueName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
