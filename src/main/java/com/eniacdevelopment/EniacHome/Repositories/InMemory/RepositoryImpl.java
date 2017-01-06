package com.eniacdevelopment.EniacHome.Repositories.InMemory;

import com.eniacdevelopment.EniacHome.DataModel.Entity;
import com.eniacdevelopment.EniacHome.Repositories.Shared.Repository;

import java.util.Dictionary;

/**
 * Created by larsg on 1/4/2017.
 */
public class RepositoryImpl<T extends Entity> implements Repository<T> {
    private final Dictionary<String, T> memory;

    public RepositoryImpl(Dictionary<String, T> memoryList) {
        this.memory = memoryList;
    }

    @Override
    public void add(T item) {
        this.memory.put(item.Id, item);
    }

    @Override
    public void add(Iterable<T> items) {
        for (T item : items) {
            this.memory.put(item.Id, item);
        }
    }

    @Override
    public void update(T item) {
        //TODO currently overwrties existing. Not a real update implementation.
        this.memory.put(item.Id, item);
    }

    @Override
    public void delete(String Id) {
        this.memory.remove(Id);
    }

    @Override
    public T get(String Id) {
        return this.memory.get(Id);
    }

    @Override
    public Iterable<T> getAll() {
        return (Iterable<T>) this.memory.elements();
    }
}
