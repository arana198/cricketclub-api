package com.cricketclub.common.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResource {
    private final String code;
    private final String message;
    private List<FieldErrorResource> fieldErrors;

    public ErrorResource(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}