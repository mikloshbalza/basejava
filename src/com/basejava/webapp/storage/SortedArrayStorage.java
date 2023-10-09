package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void fillDeletedElement(int index) {
        int amountMoved = size - index - 1;
        if (amountMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, amountMoved);
        }
    }

    @Override
    protected void insertResume(Resume r, int index) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = r;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }


}
