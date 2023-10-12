package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

}