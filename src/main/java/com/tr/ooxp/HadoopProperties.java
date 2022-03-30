package com.tr.ooxp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@Builder
@ConfigurationProperties(prefix = "hadoop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HadoopProperties {
    private String root;
    private String keyTab;
    private String jaasFile;
    private String krb5File;
    private String hbaseSite;
    private String coreSite;
    private String hBaseInstancesTable;
    private String hbaseNameSpace;
    private String hadoopUser;
    private String binDir;
    private String impalaConnectionUrl;
    private String impalaJdbcDriver;
    private String impalaDatabase;

    public String getHbaseSite() {
        return hbaseSite.replace("\\$\\{config\\.root\\}", root);
    }

    public String getJaasFile() {
        return new File(jaasFile).getAbsolutePath();
    }

    public String getKrb5File() {
        return new File(krb5File).getAbsolutePath();
    }

    public String getKeyTabFile() {
        return new File(keyTab).getAbsolutePath();
    }

}