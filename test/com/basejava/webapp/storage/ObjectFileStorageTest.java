package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializtion.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}