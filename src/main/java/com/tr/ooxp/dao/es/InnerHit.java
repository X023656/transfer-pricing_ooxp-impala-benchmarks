package com.tr.ooxp.dao.es;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InnerHit {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_type")
    private String type;

    @JsonProperty("_index")
    private String index;

    @JsonProperty("_source")
    private JsonNode source;
}