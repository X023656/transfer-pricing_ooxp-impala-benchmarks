package com.tr.ooxp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static org.apache.commons.lang.StringUtils.removeEnd;

@Component
public class ImpalaServiceImpl implements ImpalaService {

    private final Connection connection;

    @Autowired
    public ImpalaServiceImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getTables(final String pattern) throws SQLException {

        String normalizedPattern = isEmpty(pattern) ? pattern : removeEnd(pattern.replaceAll("-", "_"), "%");
        ResultSet rs = connection.createStatement().executeQuery("SHOW TABLES ");

        List<String> tables = new ArrayList<>();
        while (rs.next()) {
            String tableName = rs.getString(1);
            if (isEmpty(normalizedPattern) || tableName.startsWith(normalizedPattern))
                tables.add(tableName);
        }
        return tables;
    }

    @Override
    public List<String> getAllTables() throws SQLException {
        return getTables("");
    }

    final Random random = new Random();

    @Override
    public int count(String table) {
        return Math.abs(random.nextInt(3000000));
    }

}
