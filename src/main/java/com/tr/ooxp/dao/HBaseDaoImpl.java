package com.tr.ooxp.dao;

import com.tr.ooxp.services.ThrowingFunction;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HBaseDaoImpl implements HBaseDao {

    private final Connection connection;

    @Autowired
    public HBaseDaoImpl(final Connection connection) {
        this.connection = connection;
    }

    private Table getTable(final String tableName) throws IOException {
        return connection.getTable(TableName.valueOf(tableName));
    }

    @Override
    public List<String> listTables() throws IOException {
        return Arrays.stream(connection.getAdmin().listTables()).map(HTableDescriptor::getNameAsString).collect(Collectors.toList());
    }

    @Override
    public <T> T get(final String tableName, final String rowName, final RowMapper<T> rowMapper) throws IOException {

        Get get = new Get(Bytes.toBytes(rowName));

        Result result = getTable(tableName).get(get);

        return result.isEmpty() ? null : rowMapper.mapRow(result, 0);
    }

    @Override
    public <T> List<T> list(final String tableName, final String family, final RowMapper<T> rowMapper) throws IOException {
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(family));

        return list(tableName, scan, rowMapper);
    }

    @Override
    public <T> List<T> list(final String tableName, final String family, final List<String> columns, final RowMapper<T> rowMapper) throws IOException {
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(family));

        for (String column : columns) {
            scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }

        return list(tableName, scan, rowMapper);
    }

    <T> List<T> list(final String tableName, final Scan scan, final RowMapper<T> rowMapper) throws IOException {
        List<T> results = new ArrayList<>();

        ResultScanner scanner = getTable(tableName).getScanner(scan);
        for (Result rr : scanner) {
            results.add(rowMapper.mapRow(rr, 0));
        }

        return results;
    }


    @Override
    public long scanAndConsumeResults(final String tableName, final ThrowingFunction<Result, Integer, IOException> resultConsumer) throws IOException, InterruptedException {
        long counter = 0;
        ResultScanner scanner = getTable(tableName).getScanner(new Scan());
        for (Result rr : scanner) {
            counter += resultConsumer.apply(rr);
        }
        return counter;
    }
}
