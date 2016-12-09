package com.eniacdevelopment.EniacHome.Repositories.Shared;

import com.eniacdevelopment.EniacHome.DataModel.Entity;

/**
 * Created by larsg on 12/9/2016.
 */
public interface Repository<T extends Entity> {
    void add(T item);

    void add(Iterable<T> items);

    void update(T item);

    void remove(T item);

    T get(String Id);

    Iterable<T> getAll();
}
