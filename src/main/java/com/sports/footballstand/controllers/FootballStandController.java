package com.sports.footballstand.controllers;

import com.sports.footballstand.dtos.FootballTeamStandingDto;
import com.sports.footballstand.exceptions.BusinessException;
import com.sports.footballstand.exceptions.ControllerException;
import com.sports.footballstand.models.FootballStandingRequest;
import com.sports.footballstand.services.PopulateCache;
import com.sports.footballstand.services.TeamStandingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.MessageFormat;

@RestController
@RequestMapping("/api/football/team/standing")
public class FootballStandController {

    private static final Logger log = LoggerFactory.getLogger(PopulateCache.class);
    private final TeamStandingService teamStandingService;

    @Autowired
    public FootballStandController (TeamStandingService teamStandingService){
        this.teamStandingService = teamStandingService;
    }

    @GetMapping
    public ResponseEntity<?> getStandings(@RequestBody @Valid FootballStandingRequest teamStandingRequest) {

        try {
            log.debug(MessageFormat.format("In coming request: countryName={0}, teamName={1}, leagueName={2}.",
                    teamStandingRequest.getCountryName(), teamStandingRequest.getTeamName(), teamStandingRequest.getLeagueName()));
            return ResponseEntity.ok(teamStandingService.fetchTeamStanding(teamStandingRequest));
        }catch (BusinessException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            log.error(MessageFormat.format("Bad Request: ErrorCode={0}, Message={1}.",e.getErroCode(), e.getErrorMessage()));
            ControllerException controllerException = new ControllerException(e.getErroCode(), e.getErrorMessage());
            if(e.getErroCode().equals("601")) {
                status = HttpStatus.NOT_FOUND;
            }
            return new ResponseEntity<ControllerException>(controllerException, status);
        }catch (Exception e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String errorCode = "610";
            String message = "Exception in Controller";
            log.error(MessageFormat.format("Bad Request: ErrorCode={0}, Message={1}.",errorCode, message));
            ControllerException controllerException = new ControllerException(errorCode, message);
            return new ResponseEntity<ControllerException>(controllerException, status);
        }
    }
}
