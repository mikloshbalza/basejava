package com.basejava.webapp.storage;

import com.basejava.webapp.Config;
import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.time.Month;
import java.util.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();
    protected final Storage storage;
    private static final String UUID_1 = String.valueOf(UUID.randomUUID());
    private static final String UUID_2 = String.valueOf(UUID.randomUUID());
    protected static final String UUID_3 = String.valueOf(UUID.randomUUID());
    private static final String UUID_4 = String.valueOf(UUID.randomUUID());
    private static final String UUID_5 = String.valueOf(UUID.randomUUID());
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final String FULL_NAME_1 = "1 1";
    private static final String FULL_NAME_2 = "2 2";
    protected static final String FULL_NAME_3 = "3 3";
    private static final String FULL_NAME_4 = "4 4";
    private static final String FULL_NAME_5 = "5 5";
    private static final String UUID_NOT_EXIST = "dummy";

    static {
        RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
        RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
        RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
        RESUME_4 = new Resume(UUID_4, FULL_NAME_4);

        RESUME_1.addContact(ContactType.EMAIL, "mail1@ya.ru");
        RESUME_1.addContact(ContactType.NUMBER, "11111");

        RESUME_4.addContact(ContactType.NUMBER, "4444");
        RESUME_4.addContact(ContactType.SKYPE,"Skype");
        /*RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivement1", "Achivement12", "Achivement13"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("http://Organization11.ru", "Organization11",
                                new Period("position1", "content1", 2005, Month.JANUARY),
                                new Period("position2", "content2", 2001, Month.MARCH, 2005, Month.JANUARY))));
        RESUME_1.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("http://institute.ru", "Institute",
                                new Period("aspirant", "description-aspirant", 1996, Month.JANUARY, 2000, Month.DECEMBER),
                                new Period("student", "IT facultet", 2001, Month.MARCH, 2005, Month.JANUARY))
                ));
        RESUME_2.addContact(ContactType.SKYPE,"skype2");
        RESUME_2.addContact(ContactType.NUMBER, "22222");
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("http://company2.ru","Company2",
                                new Period("position1","content1",2015,Month.JANUARY))));*/
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertGet(RESUME_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_5);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "New Name");
        newResume.addContact(ContactType.EMAIL, "new_mail@google.com");
        newResume.addContact(ContactType.SKYPE, "new skype");
        newResume.addContact(ContactType.NUMBER, "new number");
        storage.update(newResume);
        Assert.assertTrue(newResume.equals(storage.get(UUID_1)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    public void assertSize(int expected) {
        Assert.assertEquals(expected, storage.size());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3, list.size());
        List<Resume> sortedResumes = Arrays.asList(RESUME_1,RESUME_2,RESUME_3);
        Collections.sort(sortedResumes);
        Assert.assertEquals(sortedResumes, list);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    public void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }
}
