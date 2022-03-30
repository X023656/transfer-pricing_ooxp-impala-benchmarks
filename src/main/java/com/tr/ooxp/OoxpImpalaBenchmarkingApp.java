package com.tr.ooxp;

import com.tr.ooxp.services.BenchmarkManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OoxpImpalaBenchmarkingApp implements CommandLineRunner {

    private static final Logger log  = LoggerFactory.getLogger(OoxpImpalaBenchmarkingApp.class);

    @Value("${instances:}")
    private String instances;

    @Value("${aws_profile:}")
    private String awsProfile;

    private final BenchmarkManager benchmarkManager;

    @Autowired
    public OoxpImpalaBenchmarkingApp(final BenchmarkManager benchmarkManager) {
        this.benchmarkManager = benchmarkManager;
    }

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(OoxpImpalaBenchmarkingApp.class);
        app.setBannerMode(Banner.Mode.OFF);

        // java -jar /path/to/this.jar --spring.profiles.active=sat --instances={instance1,instance2,...,instanceN}

        app.run(args);
    }

    @Override
    public void run(String... args) {

        if (StringUtils.isEmpty(instances)) {
            showCliOptions();
            throw new IllegalArgumentException("instance is a required parameter");
        }

        for (String instance : instances.split(",")) {
            try {
                benchmarkManager.benchmark(instance);
            } catch (Exception e) {
                log.error("ReSync for instance {} unsuccessful [{}]", instance, e.getMessage());
            }
        }
    }

    private void showCliOptions() {
        log.error("Arguments: --instances={i1,i2..iN} ");
    }
}
