package com.tr.ooxp.dao.es;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexResponse {
    @JsonProperty("_index")
    private String index;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_version")
    private long version;
    private boolean created;
    private String result;
    @JsonProperty("_shards")
    private Shards shards;

    @Data
    @NoArgsConstructor
    public static class Shards {
        private int total;
        private int failed;
        private int successful;
    }
}

