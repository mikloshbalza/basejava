package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        if (size < 10000) { //save на переполнение storage
            if (get(r.getUuid()) == null) { //save на отсутствие резюме в storage
                storage[size] = r;
                size++;
            } else System.out.println("Резюме с таким uuid уже существует в базе.");
        } else System.out.println("Достигнуто максимальное количество резюме в базе. Сохранение невозможно.");
    }

    public void update(Resume r)  throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (get(r.getUuid()) != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    Resume resume = new Resume();
                    System.out.print("Введите новый uuid: ");
                    resume.setUuid(reader.readLine());
                    storage[i] = resume;
                }
            }
        } else System.out.println("Резюме с таким uuid не существует в базе.");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        if (get(uuid) != null) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    storage[i] = storage[size - 1];
                    size--;
                }
            }
        }

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
}
