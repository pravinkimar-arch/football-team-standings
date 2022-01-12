package com.sports.footballstand.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;


//@Data
////@Getter
////@Setter
////@AllArgsConstructor
////@NoArgsConstructor
////Issue with lombak plugin for IDE
public class League{

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("league_name")
    private String leagueName;

    @JsonProperty("league_id")
    private String leagueId;

    @JsonProperty("country_id")
    private String countryId;

    public League(String countryName, String leagueName, String leagueId, String countryId){
        this.countryName = countryName;
        this.leagueName = leagueName;
        this.leagueId = leagueId;
        this.countryId = countryId;
    }

    public League(){}

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}
