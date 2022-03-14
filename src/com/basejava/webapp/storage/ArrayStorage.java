package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size; // количество резюме в массиве

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size < storage.length) { //save на переполнение storage
            if (!existence(r.getUuid())) { //save на отсутствие резюме в storage
                storage[size] = r;
                size++;
            } else System.out.println("Резюме с id: " + r.getUuid() + " уже существует.");
        } else System.out.println("Достигнуто максимальное количество резюме в базе. Сохранение невозможно.");
    }

    public void update(Resume r) {
        if (existence(r.getUuid())) {
            storage[searchResume(r.getUuid())] = r;
        } else System.out.println("Резюме с id: " + r.getUuid() + " не найдено.");
    }

    public Resume get(String uuid) {
        if (existence(uuid)) {
            return storage[searchResume(uuid)];
        } else System.out.println("Резюме с id: " + uuid + " не найдено.");

        return null;
    }

    public void delete(String uuid) {
        if (existence(uuid)) {
            storage[searchResume(uuid)] = storage[size - 1];
            size--;
        } else System.out.println("Резюме с id: " + uuid + " не найдено.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    Integer searchResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    boolean existence(String uuid) {
        return searchResume(uuid) != null;
    }
}
