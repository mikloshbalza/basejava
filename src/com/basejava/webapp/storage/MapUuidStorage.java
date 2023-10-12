package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (Objects.equals(entry.getKey(), uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }


    @Override
    public int size() {
        return storage.size();
    }
}
