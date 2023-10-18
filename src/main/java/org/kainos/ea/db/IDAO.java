package org.kainos.ea.db;

import org.kainos.ea.client.*;
;

import java.util.List;

public interface IDAO<T> {
    List<T> getAll() throws FailedToFetchException;
    T getById(int id) throws FailedToFetchException, NotFoundException;

    int create(T obj) throws FailedToCreateException;
    void update(int id, T obj) throws FailedToUpdateException;
    void delete(int id) throws FailedToDeleteException;
}
