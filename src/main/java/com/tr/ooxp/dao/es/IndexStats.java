package com.tr.ooxp.dao.es;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Data
public class IndexStats {
    private String index;
    private Instant start;
    private Instant end;
    private int shardsSaved;
    private boolean isSuccess;
    private String message;

    public IndexStats(final String index) {
        this.index = index;
        this.start = Instant.now();
    }

    public void markEnd() {
        this.end = Instant.now();
    }

    public long getDuration() {
        if (start==null || end == null)
            return -1;
        return Duration.between(start, end).toMillis();
    }
}
