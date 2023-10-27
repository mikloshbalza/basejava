package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializtion.ObjectStreamSerializer;
import com.basejava.webapp.storage.serializtion.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}