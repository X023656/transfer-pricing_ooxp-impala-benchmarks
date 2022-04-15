package com.tr.ooxp.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ImpalaService {
    public List<String> getTables (String pattern) throws SQLException;

    public List<String> getAllTables() throws SQLException;

    public int count(String table) throws SQLException ;

    public List<String> getTableNamesFromCsv(String fileNameWithPath);

    public List<String> getQueriesFromFile(String fileNameWithPath);

    public void exeQuery(String query) throws SQLException;

    public void writeToCsv(List<String> impalaTables, String fileNameWithPath, boolean append) throws Exception;
}
