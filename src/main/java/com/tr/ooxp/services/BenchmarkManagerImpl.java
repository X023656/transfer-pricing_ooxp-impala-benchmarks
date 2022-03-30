package com.tr.ooxp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.ooxp.HadoopProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
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

    @Override
    public void benchmark(String instanceName) throws Exception {

//        List<Instance> instances = hBaseService.getInstances();

        List<String> impalaTables = impalaService.getTables(instanceName);

        StringBuilder sb = new StringBuilder();

        for (String table : impalaTables) {

            Instant tableStart = Instant.now();

            int count = impalaService.count(table);

            long duration = Duration.between(tableStart, Instant.now()).getSeconds();

            System.out.printf("%s complete. count: %d   Took %d seconds\n", table, count, duration);
            sb.append(String.format("%s,%d,%d\n", table, count, duration));
        }

        System.out.println("=========================================================");
        System.out.println("Table,Rows,Duration");
        System.out.println(sb);
        System.out.println("=========================================================");
        System.out.printf(" Total time: %.2f mins\n",                 Duration.between(start, Instant.now()).getSeconds() / 60f);
        System.out.println("=========================================================");

        // write logic here
    }

}
