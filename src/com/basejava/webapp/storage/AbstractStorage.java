package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collection;

public abstract class AbstractStorage implements Storage {
    //protected Collection<Resume> storage;

    @Override
    public void save(Resume r) {
        doSave(r, getNotExistingSearchKey(r.getUuid()));
    }


    @Override
    public void update(Resume r) {
        doUpdate(r, getExistingSearchKey(r.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        doDelete(getExistingSearchKey(uuid));
    }


    public Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException("Resume with uuid: " + uuid + " doesn't exist.");
        } else {
            return searchKey;
        }

    }

    public Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException("Resume with uuid: " + uuid + " already exist.");
        } else {
            return searchKey;
        }
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    public abstract void clear();

    public abstract Resume[] getAll();

    public abstract int size();
}
