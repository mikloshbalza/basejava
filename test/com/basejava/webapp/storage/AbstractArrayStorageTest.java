package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume("1"));
        storage.save(new Resume("2"));
        storage.save(new Resume("3"));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.clear();
        storage.save(new Resume());
        Assert.assertEquals(1, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("2");
        storage.get("2");
    }

    @Test
    public void update() {
        Resume resume = new Resume("4");
        storage.save(resume);
        storage.update(resume);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(storage.get("1"), resumes[0]);
        Assert.assertEquals(storage.get("2"), resumes[1]);
        Assert.assertEquals(storage.get("3"), resumes[2]);
    }

    @Test
    public void get() {
        Resume resume = new Resume("4");
        storage.save(resume);
        Assert.assertEquals(resume.hashCode(), storage.get("4").hashCode());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail("Storage overflow occured prematurely");
        }
        storage.save(new Resume());
    }
}
