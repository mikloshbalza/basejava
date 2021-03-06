package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size; // количество резюме в массиве

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size < STORAGE_LIMIT) { //проверка на переполнение
            if (!isExist(index)) { //проверка на наличие резюме в storage
                insertResume(r, index);
                size++;
            } else System.out.println("Резюме с id: " + r.getUuid() + " уже существует.");
        } else System.out.println("Достигнуто максимальное количество резюме в базе. Сохранение невозможно.");
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        } else System.out.println("Резюме с id: " + uuid + " не найдено.");
    }


    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (isExist(index)) {
            storage[index] = r;
        } else System.out.println("Резюме с id: " + uuid + " не найдено.");
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
        System.out.println("Резюме с id: " + uuid + " не найдено.");
        return null;
    }

    protected boolean isExist(int index) {
        return index >= 0;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertResume(Resume r, int index);

    protected abstract int findIndex(String uuid);


}
