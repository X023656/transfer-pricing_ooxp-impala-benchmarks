package com.tr.ooxp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
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
    public int count(String table) throws SQLException {

        ResultSet rs = connection.createStatement().executeQuery("SELECT COUNT(*) FROM "+table);
        rs.next();
        return rs.getInt(1);
    }


    @Override
    public List<String> getTableNamesFromCsv(String fileNameWithPath) {
        String line = "";
        String splitBy = ",";
        List<String> tableNamesList = new ArrayList<>();
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(fileNameWithPath));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] table = line.split(splitBy);
                tableNamesList.add(table[0]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return tableNamesList;
    }

    @Override
    public List<String> getQueriesFromFile(String fileNameWithPath) {
        String line = "";
        List<String> selectQueriesList = new ArrayList<>();
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(fileNameWithPath));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                selectQueriesList.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return selectQueriesList;
    }

    @Override
    public void exeQuery(String query) throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery(query);
    }
    @Override
    public void writeToCsv(List<String> impalaTables, String fileNameWithPath, boolean append) throws Exception{

        File csvOutputFile = new File(fileNameWithPath);
        FileWriter fileWriter = new FileWriter(csvOutputFile, append);
        for(String table : impalaTables) {

            fileWriter.write( table + ",\n");
        }
        fileWriter.close();

    }
}
