package com.tr.ooxp.dao.es;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = IndexBulkResponse.class, name = "index"),
        @JsonSubTypes.Type(value = CreateBulkResponse.class, name = "create")
})
public class BulkActionResponse {
    @JsonProperty(value = "_index")
    private String index;
    @JsonProperty(value = "_type")
    private String type;
    @JsonProperty(value = "_id")
    private String id;
    @JsonProperty(value = "_version")
    private int version;
    private int status;
    private BulkActionErrorResponse error;
}