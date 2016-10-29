package com.cricketclub.team.controller;

import com.cricketclub.team.dto.TeamList;
import com.cricketclub.team.exception.NoSuchTeamException;
import com.cricketclub.team.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/v1.0/teams")
public class TeamController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

    private final TeamService teamService;
    private final TeamControllerHateoasBuilder teamControllerHateoasBuilder;

    @Autowired
    public TeamController(final TeamService teamService, final TeamControllerHateoasBuilder teamControllerHateoasBuilder) {
        this.teamService = teamService;
        this.teamControllerHateoasBuilder = teamControllerHateoasBuilder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TeamList> getActiveTeams() throws NoSuchTeamException {
        LOGGER.info("Getting member roles");
        TeamList teamList = teamService.getActiveTeams()
                .orElseThrow(() -> new NoSuchTeamException());
        //teamList.add(teamControllerHateoasBuilder.buildLinksForGetCommitteeRoles());
        return new ResponseEntity<TeamList>(teamList, HttpStatus.OK);
    }
}
