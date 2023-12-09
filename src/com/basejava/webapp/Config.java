package com.basejava.webapp;

import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Config {
    protected static final File PROPS = new File("C:\\Users\\tkach\\Desktop\\java\\startjava\\basejava\\config\\resumes.properties");
    private static final Config OUR_INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;
    private Set<String> immutableUuids = new HashSet<String>() {{  // for JDK 9+: Set.of("111", "222");
        add("11111111-1111-1111-1111-111111111111");
        add("22222222-2222-2222-2222-222222222222");
    }};

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

    public boolean isImmutable(String uuids){
        return immutableUuids.contains(uuids);
    }

    public void checkImmutable(String uuids){
        if (immutableUuids.contains(uuids))
            throw new RuntimeException("Зарезервированные резюме нельзя менять");

    }

}