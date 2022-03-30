package com.tr.ooxp.dao;

import org.apache.hadoop.hbase.client.ResultScanner;

import java.util.List;

public interface ResultExtractor<T> {
    List<T> extract(ResultScanner results);
}
