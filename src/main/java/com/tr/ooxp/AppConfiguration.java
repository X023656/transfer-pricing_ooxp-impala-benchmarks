package com.tr.ooxp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.sql.DriverManager;

import static org.apache.hadoop.security.UserGroupInformation.*;

@Configuration
@EnableConfigurationProperties({HadoopProperties.class, AppProperties.class})
public class AppConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AppConfiguration.class);


    @Bean
    public org.apache.hadoop.conf.Configuration getHadoopConfig(final HadoopProperties props) {

        System.setProperty("java.security.krb5.conf", props.getKrb5File());
        System.setProperty("java.security.auth.login.config", props.getJaasFile());

        if (System.getProperty("os.name").toLowerCase().startsWith("windows"))
            System.setProperty("hadoop.home.dir", props.getBinDir());

        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
        config.set("hadoop.security.authentication", "kerberos");
        config.set("hbase.security.authentication", "kerberos");

        config.addResource(new Path(props.getCoreSite()));
//        config.addResource(new Path(props.getHdfsSite()));
        config.addResource(new Path(props.getHbaseSite()));

        log.info("HBase site xml path : " + props.getHbaseSite());
        config.addResource(new Path(props.getHbaseSite()));

        return config;
    }

    @Bean
    public UserGroupInformation getUserGroupInformation(final HadoopProperties props,
                                                        final org.apache.hadoop.conf.Configuration config) throws IOException {
        setConfiguration(config);
        loginUserFromKeytab(props.getHadoopUser(), props.getKeyTabFile());
        return loginUserFromKeytabAndReturnUGI(props.getHadoopUser(), props.getKeyTabFile());
    }

    @Bean
    public Connection getHbaseConnection(final org.apache.hadoop.conf.Configuration config,
                                         final UserGroupInformation userGroupInformation) throws IOException, InterruptedException {
        return (Connection) userGroupInformation.doAs((PrivilegedExceptionAction<Object>) () -> ConnectionFactory.createConnection(config));
    }

    @Bean
    public java.sql.Connection getImpalaConnection(final UserGroupInformation userGroupInformation,
                                                   final HadoopProperties hadoopProperties)
            throws ClassNotFoundException, IOException, InterruptedException {
        Class.forName(hadoopProperties.getImpalaJdbcDriver());
        return (java.sql.Connection) userGroupInformation.doAs((PrivilegedExceptionAction<Object>) () ->
                DriverManager.getConnection(hadoopProperties.getImpalaConnectionUrl()));
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
