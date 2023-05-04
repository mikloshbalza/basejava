package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void save(Resume r) {
        if (storage.contains(r)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            storage.add(r);
        }
    }

    @Override
    public void update(Resume r) {
        if (!storage.contains(r)) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage.set(storage.indexOf(r), r);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume r = new Resume(uuid);
        if (!storage.contains(r)) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage.get(storage.indexOf(r));
        }
    }
}
