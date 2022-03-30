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
        int moved = 0;
        int notPresent = 0;

        for (String table : impalaTables) {

            System.out.println(table);

            Instant begin = Instant.now();
            long duration = Duration.between(begin, Instant.now()).getSeconds();
            System.out.printf("%s complete. Took %d seconds\n", table, duration);
            sb.append(String.format("%s,%d\n", table, duration));
            moved++;
        }


        System.out.println("=========================================================");
        System.out.println("Table,Rows,Duration");
        System.out.println(sb);
        System.out.println("=========================================================");
        System.out.printf(" Locators: %d     Moved: %d     Table not present: %d   Total time: %.2f mins\n",
                moved + notPresent, moved, notPresent, Duration.between(start, Instant.now()).getSeconds() / 60f);
        System.out.println("=========================================================");
    }

}
