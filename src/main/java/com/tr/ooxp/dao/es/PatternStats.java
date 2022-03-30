package com.tr.ooxp.dao.es;

import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class PatternStats {
    private String pattern;
    private List<IndexStats> indices;
    private Instant start;
    private Instant end;

    public PatternStats(final String pattern) {
        this.pattern = pattern;
        indices = new ArrayList<>();
    }

    public void addIndex(final IndexStats indexStat) {
        indices.add(indexStat);
    }

    public long timeElapsed() {
        if (start==null || end == null)
            return -1;
        return Duration.between(start, end).toMillis();
    }

    public long getSuccesfulCount() {
        return indices.stream().filter(IndexStats::isSuccess).count();
    }

    public long getShardsSaved() {
        return indices.stream().map(IndexStats::getShardsSaved).reduce(Integer::sum).orElse(0);
    }

}
