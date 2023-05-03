package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size < STORAGE_LIMIT) { //overflow check
            if (!isExist(index)) { //check if resume present in storage
                insertResume(r, index);
                size++;
            } else throw new ExistStorageException(r.getUuid());
        } else throw new StorageException("Storage overflow", r.getUuid());
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        } else throw new NotExistStorageException(uuid);
    }


    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (isExist(index)) {
            storage[index] = r;
        } else throw new NotExistStorageException(r.getUuid());
    }

    public int size() {
        return size;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);

    }

    protected boolean isExist(int index) {
        return index >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertResume(Resume r, int index);

    protected abstract int findIndex(String uuid);


}
