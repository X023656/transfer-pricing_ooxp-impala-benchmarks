package com.tr.ooxp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.ooxp.HadoopProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class BenchmarkManagerImpl implements BenchmarkManager {

    private static final Logger log = LoggerFactory.getLogger(BenchmarkManagerImpl.class);
    private final HadoopProperties hadoopProperties;
    private final ImpalaService impalaService;
    private final ObjectMapper mapper;
    private final Instant start;

    @Autowired
    public BenchmarkManagerImpl(final HadoopProperties hadoopProperties,
                                final ImpalaService impalaService,
                                final ObjectMapper mapper) {
        this.hadoopProperties = hadoopProperties;
        this.impalaService = impalaService;
        this.mapper = mapper;
        this.start = Instant.now();
    }

    @Value("${file.table.path}")
    private String fileTablePath;

    @Value("${file.table.result.path}")
    private String fileTableResultPath;

    @Value("${file.query.path}")
    private String fileQueryPath;

    @Value("${file.query.result.path}")
    private String fileQueryResultPath;

    public static boolean isValidFilePath(String path) {
        File f = new File(path);
        try {
            f.getCanonicalPath();
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    @Override
    public void benchmark(String instanceName) throws Exception {

        if(!isValidFilePath(fileTablePath)) {
            /**To read the table names from database*/
            List<String> impalaTables = impalaService.getTables(instanceName);
            /**To write the tables names to csv*/
            impalaService.writeToCsv(impalaTables, fileTablePath, false);
        } else {
            getTableWiseRecordCountBenchmark(fileTablePath, fileTableResultPath);
        }
        getSelectQueryExecuteTimeBenchmark(fileQueryPath, fileQueryResultPath);
    }

    @Override
    public void getTableWiseRecordCountBenchmark(String inputPath, String outputPath) throws Exception {

        /**To read the table names from csv file*/
         List<String> impalaTables = impalaService.getTableNamesFromCsv(inputPath);

        List<String> result = new ArrayList<>();
        for (String table : impalaTables) {
            Instant tableStart = Instant.now();
            /**get the count of table */
            int count = impalaService.count(table);

            long duration = Duration.between(tableStart, Instant.now()).toMillis();
            result.add(String.format("%s,%d,%d", table, count, duration));
        }
        impalaService.writeToCsv(result,outputPath, false);
    }

    @Override
    public void getSelectQueryExecuteTimeBenchmark(String inputFilePath, String outputFilePath) throws Exception {

        /**To read the Queries  from txt file*/
        List<String> impalaQueries = impalaService.getQueriesFromFile(inputFilePath);
        List<String> result = new ArrayList<>();
        for (String query : impalaQueries) {
            Instant tableStart = Instant.now();
            /** execute select queries **/
            impalaService.exeQuery(query);
            long duration = Duration.between(tableStart, Instant.now()).toMillis();
            result.add(String.format("%s,%d", query, duration));
        }
        impalaService.writeToCsv(result,outputFilePath, false);
    }
}
