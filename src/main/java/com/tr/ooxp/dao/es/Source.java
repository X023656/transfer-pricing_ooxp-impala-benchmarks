package com.tr.ooxp.dao.es;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Source {
    @JsonProperty(value = "@timestamp")
    private String timeStamp;

    @JsonProperty(value = "Message")
    private String message;
}