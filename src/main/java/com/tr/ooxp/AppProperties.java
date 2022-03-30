package com.tr.ooxp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "general")
@Data
public class AppProperties {
    private int concurrency;
    private String appName;
}