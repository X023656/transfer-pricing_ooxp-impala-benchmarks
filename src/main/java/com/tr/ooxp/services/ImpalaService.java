package com.tr.ooxp.services;

import java.sql.SQLException;
import java.util.List;

public interface ImpalaService {
    List<String> getTables (String pattern) throws SQLException;

    List<String> getAllTables() throws SQLException;

    int count(String table);
}
