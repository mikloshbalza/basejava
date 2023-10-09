package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapFullNameStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object getSearchKey(String fullName) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (Objects.equals(entry.getKey(), fullName)) {
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
    public void save(Resume r) {
        doSave(r, getNotExistingSearchKey(r.getFullName()));
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put(r.getFullName(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    public void update(Resume r) {
        doUpdate(r, getExistingSearchKey(r.getFullName()));
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
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> values = new ArrayList<>(storage.values());
        Collections.sort(values, RESUME_COMPARATOR);
        return values;
    }


    @Override
    public int size() {
        return storage.size();
    }
}
