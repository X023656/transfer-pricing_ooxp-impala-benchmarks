package com.tr.ooxp.dao.es;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexMetaData {

    private String health;

    private String status;

    @JsonProperty("index")
    private String name;

    @JsonProperty("pri")
    private int shards;

    @JsonProperty("rep")
    private int replicas;

    @JsonProperty("docs.count")
    private int docs;

    @JsonProperty("docs.deleted")
    private int deleted;

    @JsonProperty("store.size")
    private long size;

    @JsonProperty("pri.store.size")
    private long primarySize;

    public int getTotalShards() {
        return this.shards * (this.replicas + 1);
    }

    public String getInstance() {
        return name.split("_")[1];
    }

    public String getNameWithoutSuffix(final String suffix) {
        return getName().replaceAll(suffix + "$", "");
    }

    @Override
    public String toString() {
        return String.format("name:%-50s shards:%d replicas:%d", this.name, this.shards, this.replicas);
    }

}
