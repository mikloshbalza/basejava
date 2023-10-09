package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> {
        if (o1.getFullName().equals(o2.getFullName())) {
            return o1.getUuid().compareTo(o2.getUuid());
        } else {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    };

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


    protected Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException("Resume with uuid: " + uuid + " doesn't exist.");
        } else {
            return searchKey;
        }

    }

    protected Object getNotExistingSearchKey(String uuid) {
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

    public abstract List<Resume> getAllSorted();

    public abstract int size();
}
