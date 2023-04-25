package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    static final String UUID_1 = "1";
    static final String UUID_2 = "2";
    static final String UUID_3 = "3";
    static final String UUID_4 = "4";
    static final String UUID_5 = "5";
    static final Resume RESUME_1 = new Resume(UUID_1);
    static final Resume RESUME_2 = new Resume(UUID_2);
    static final Resume RESUME_3 = new Resume(UUID_3);

    static final String UUID_NOT_EXIST = "dummy";

    static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Assert.assertArrayEquals(new Resume[0], storage.getAll());

    }

    @Test
    public void save() {
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_5);
    }

    @Test
    public void update() {
        storage.update(RESUME_3);
        Assert.assertSame(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    public void assertSize(int expected) {
        Assert.assertEquals(expected, storage.size());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void getAll() {
        Resume[] actual = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(actual, storage.getAll());
    }

    @Test
    public void get() {
        assertGet(new Resume(UUID_4));
    }

    public void assertGet(Resume resume) {
        storage.save(resume);
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail("Storage overflow occured prematurely");
        }
        storage.save(new Resume());
    }
}
