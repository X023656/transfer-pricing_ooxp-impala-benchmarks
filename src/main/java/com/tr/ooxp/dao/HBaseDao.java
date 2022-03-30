package com.tr.ooxp.dao;

import com.tr.ooxp.services.ThrowingFunction;
import org.apache.hadoop.hbase.client.Result;

import java.io.IOException;
import java.util.List;

public interface HBaseDao {
    List<String> listTables() throws IOException;

    <T> T get(String tableName, String rowName, RowMapper<T> rowMapper) throws IOException;

    <T> List<T> list(String tableName, String family, RowMapper<T> rowMapper) throws IOException;

    <T> List<T> list(String tableName, String family, List<String> columns, RowMapper<T> rowMapper) throws IOException;

    long scanAndConsumeResults(String tableName, ThrowingFunction<Result, Integer, IOException> resultConsumer) throws IOException, InterruptedException;
}
