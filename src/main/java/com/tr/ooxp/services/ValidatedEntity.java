package com.tr.ooxp.services;

import com.tr.ooxp.dao.RowMapper;

import java.util.concurrent.BlockingQueue;

public interface ValidatedEntity<T> {

    RowMapper<T> getRowMapper();

    String getEntitySuffix();

    String getHistorySuffix();

    BlockingQueue<T> getEntityQueue();

    BlockingQueue<T> getHistoryQueue();

    T lastElement();

    boolean isLast(final T hierarchy);

    void compare() throws InterruptedException;
}
