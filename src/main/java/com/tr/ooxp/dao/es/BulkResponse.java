package com.tr.ooxp.dao.es;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkResponse {
    private long took;
    private List<BulkActionResponse> items;
    private boolean errors;
}
