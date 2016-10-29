package com.cricketclub.team.dto;

import com.cricketclub.common.dto.BaseDomain;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Team extends BaseDomain {
    private final Long teamId;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final Boolean active;

    @JsonCreator
    public Team(@JsonProperty(value = "teamId") final Long teamId,
                @JsonProperty(value = "name", required = true) final String name,
                @JsonProperty(value = "description", required = true) final String description,
                @JsonProperty(value = "imageUrl", required = false) final String imageUrl,
                @JsonProperty(value = "active", required = false) final Boolean active) {
        this.teamId = teamId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = active;
    }
}
