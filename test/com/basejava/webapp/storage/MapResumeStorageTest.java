package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getFullName()));
    }

    @Override
    @Test
    public void update() {
        storage.update(RESUME_3);
        Assert.assertSame(RESUME_3, storage.get(FULL_NAME_3));
    }

}