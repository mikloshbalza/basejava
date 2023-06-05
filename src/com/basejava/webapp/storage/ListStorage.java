package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.contains((Resume) searchKey);
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.add((Resume) searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(storage.indexOf(searchKey));
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.set(storage.indexOf((Resume) searchKey), (Resume) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((Resume) searchKey);
    }
}
