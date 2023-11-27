package com.basejava.webapp;

import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File("C:\\Users\\tkach\\Desktop\\java\\startjava\\basejava\\config\\resumes.properties");
    private static final Config OUR_INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;

    public static Config getInstance() {
        return OUR_INSTANCE;
    }

    private Config() {
        try(InputStream is = Files.newInputStream(PROPS.toPath())){
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"),props.getProperty("db.user"),props.getProperty("db.password"));
        }catch (IOException e){
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public File getStorageDir() {
        return storageDir;
    }

}