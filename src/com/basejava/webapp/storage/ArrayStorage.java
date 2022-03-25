package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        if (size < STORAGE_LIMIT) { //проверка на переполнение
            if (!isExist(findIndex(uuid))) { //проверка на наличие резюме в storage
                storage[size] = r;
                size++;
            } else System.out.println("Резюме с id: " + uuid + " уже существует.");
        } else System.out.println("Достигнуто максимальное количество резюме в базе. Сохранение невозможно.");
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = findIndex(uuid);
        if (isExist(index)) {
            storage[index] = r;
        } else System.out.println("Резюме с id: " + uuid + " не найдено.");
    }



    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (isExist(index)) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else System.out.println("Резюме с id: " + uuid + " не найдено.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }



    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(int index) {
        return index >= 0;
    }
}
