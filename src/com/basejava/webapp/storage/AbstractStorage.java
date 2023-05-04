package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Collection;

public class AbstractStorage implements Storage {
    protected Collection<Resume> storage;

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume r) {
    }

    @Override
    public void update(Resume r) {
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {
        storage.remove(new Resume(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
