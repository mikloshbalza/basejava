/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size; //вводим переменную для текущего размера(кол-ва резюме в базе)

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (get(r.uuid) == null) {
            storage[size()] = r;
            size++;
        } else {
            System.out.println("Резюме с uuid = " + r.uuid + "уже существует.");
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                storage[i] = null;
                size--;
            } else {
                if (storage[i].uuid.equals(uuid)) {
                    for (int j = i; j < size; j++) {
                        storage[j] = storage[j + 1];
                    }
                    size--;
                    break;
                }
            }

        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size];

        for (int i = 0; i < size; i++) {
            all[i] = storage[i];
        }
        return all;
    }

    int size() {
        return size;
    }
}
