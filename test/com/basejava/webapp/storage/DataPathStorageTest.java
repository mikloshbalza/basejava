package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializtion.DataStreamSerializer;
import com.basejava.webapp.storage.serializtion.ObjectStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}