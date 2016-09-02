package com.cricketclub.team.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Team extends BaseDomain {
    private final Long teamId;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final Boolean active;

    @JsonCreator
    public Team(@JsonProperty(value = "teamId") Long teamId,
                @JsonProperty(value = "name", required = true) String name,
                @JsonProperty(value = "description", required = true) String description,
                @JsonProperty(value = "imageUrl", required = false) String imageUrl,
                @JsonProperty(value = "active", required = false) Boolean active) {
        this.teamId = teamId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = active;
    }
}
