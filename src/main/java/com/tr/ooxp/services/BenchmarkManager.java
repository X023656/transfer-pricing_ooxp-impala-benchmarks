package com.tr.ooxp.services;

public interface BenchmarkManager {
    public void benchmark(String instance) throws Exception;
    public void getTableWiseRecordCountBenchmark(String inputPath, String outputPath) throws Exception;
    public void getSelectQueryExecuteTimeBenchmark(String inputFilePath, String outputFilePath) throws Exception;

}
