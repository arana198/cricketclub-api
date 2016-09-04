package com.cricketclub.common.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldErrorResource {
    private final String resource;
    private final String field;
    private final String code;
    private final String message;
}
