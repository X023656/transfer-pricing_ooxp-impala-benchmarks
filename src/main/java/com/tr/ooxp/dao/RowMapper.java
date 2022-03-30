package com.tr.ooxp.dao;

import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;

public interface RowMapper<T> {
    T mapRow(Result result, int rowNum) throws IOException;


}
