package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(Resume r, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doUpdate(Resume r, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        doSave(r, getNotExistingSearchKey(r.getUuid()));
    }


    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        doUpdate(r, getExistingSearchKey(r.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        doDelete(getExistingSearchKey(uuid));
    }


    protected SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException("Resume with uuid: " + uuid + " doesn't exist.");
        } else {
            return searchKey;
        }

    }

    protected SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException("Resume with uuid: " + uuid + " already exist.");
        } else {
            return searchKey;
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }
}
