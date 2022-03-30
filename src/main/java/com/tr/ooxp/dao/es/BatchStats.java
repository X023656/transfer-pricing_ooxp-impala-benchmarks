package com.tr.ooxp.dao.es;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BatchStats {
    private List<String> patterns;
    private List<PatternStats> patternStats;

    public BatchStats(final List<String> patterns) {
        this.patterns = patterns;
    }

    public void addStats(final PatternStats indexStat) {
        if (patternStats ==null)
            patternStats = new ArrayList<>();
        patternStats.add(indexStat);
    }

    public void printReport() {

    }

}
