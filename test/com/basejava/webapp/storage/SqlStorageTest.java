package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.storage.serializtion.DataStreamSerializer;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.getInstance().getDbUrl(), Config.getInstance().getDbUser(), Config.getInstance().getDbPassword()));
    }
}