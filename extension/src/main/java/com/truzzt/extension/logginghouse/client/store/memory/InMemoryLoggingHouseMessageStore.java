/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - Initial implementation
 *       truzzt GmbH - PostgreSQL implementation
 *
 */

package com.truzzt.extension.logginghouse.client.store.memory;

import com.truzzt.extension.logginghouse.client.spi.store.LoggingHouseMessageStore;
import com.truzzt.extension.logginghouse.client.spi.types.LoggingHouseMessage;
import com.truzzt.extension.logginghouse.client.spi.types.LoggingHouseMessageStatus;
import org.eclipse.edc.spi.persistence.EdcPersistenceException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class InMemoryLoggingHouseMessageStore implements LoggingHouseMessageStore {

    private final Map<Long, LoggingHouseMessage> cache = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock lock;
    private final AtomicLong sequence;

    public InMemoryLoggingHouseMessageStore() {
        lock = new ReentrantReadWriteLock(true);
        sequence = new AtomicLong();
    }

    @Override
    public void save(LoggingHouseMessage event) {

        Objects.requireNonNull(event);
        Objects.requireNonNull(event.getEventType());
        Objects.requireNonNull(event.getEventId());
        Objects.requireNonNull(event.getEventToLog());
        Objects.requireNonNull(event.getProcessId());
        Objects.requireNonNull(event.getCreatedAt());

        lock.writeLock().lock();
        try {
            var id = sequence.addAndGet(1);
            event.setId(id);

            cache.put(event.getId(), event);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<LoggingHouseMessage> listPending() {
        lock.readLock().lock();
        try {
            return cache.values().stream()
                    .filter(event -> event.getReceipt() == null)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void updateSent(long id, String receipt) {
        lock.writeLock().lock();
        try {
            if (cache.containsKey(id)) {
                var event = cache.get(id);

                event.setReceipt(receipt);
                cache.put(event.getId(), event);
            } else {
                throw new EdcPersistenceException("Message not found with id: " + id);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void updateRetry(long id) {
        lock.writeLock().lock();
        try {
            if (cache.containsKey(id)) {
                var event = cache.get(id);

                var retries = event.getRetries();
                event.setRetries(retries + 1);

                cache.put(event.getId(), event);
            } else {
                throw new EdcPersistenceException("Message not found with id: " + id);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void updateFailed(long id) {
        lock.writeLock().lock();
        try {
            if (cache.containsKey(id)) {
                var event = cache.get(id);

                var retries = event.getRetries();
                event.setRetries(retries + 1);
                event.setStatus(LoggingHouseMessageStatus.FAILED);

                cache.put(event.getId(), event);
            } else {
                throw new EdcPersistenceException("Message not found with id: " + id);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

}
