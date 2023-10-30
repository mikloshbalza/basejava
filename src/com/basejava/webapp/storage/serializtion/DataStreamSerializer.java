package com.basejava.webapp.storage.serializtion;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                       List<String> listSection = ((ListSection) section).getList();
                        for (String item :
                                listSection) {
                            dos.writeUTF(item);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companies = ((CompanySection) section).getCompanies();
                        for (Company company :
                                companies) {
                            dos.writeUTF(company.getUrl());
                            dos.writeUTF(company.getName());
                            List<Period> periods = company.getPeriods();
                            for (Period period :
                                    periods) {
                                dos.writeUTF(period.getName());
                                dos.writeUTF(period.getDescription());
                                dos.writeInt(period.getStartDate().getYear());
                                dos.writeInt(period.getStartDate().getMonth().getValue());
                                dos.writeInt(period.getEndDate().getYear());
                                dos.writeInt(period.getEndDate().getMonth().getValue());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            //TODO implements sections
            return resume;
        }
    }
    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection) throws IOException {
        dos.writeInt(collection.size());
        for (T item :
                collection) {

        }
    }
}