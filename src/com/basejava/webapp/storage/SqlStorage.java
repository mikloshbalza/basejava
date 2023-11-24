package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.AbstractSection;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.sql.ExceptionUtil;
import com.basejava.webapp.sql.SqlHelper;
import com.basejava.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();
            }
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });

    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                preparedStatement.setString(1, r.getFullName());
                preparedStatement.setString(2, r.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r, connection);
            deleteSections(r, connection);
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume r;
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume WHERE uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, resultSet.getString("full_name"));
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM contact WHERE resume_uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, r);
                }
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM section WHERE  resume_uuid=?")){
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    addSection(resultSet, r);
                }
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM  resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    map.put(resultSet.getString("uuid"), new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, map.get(resultSet.getString("resume_uuid")));
                }
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section")){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    addSection(resultSet, map.get(resultSet.getString("resume_uuid")));
                }
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(uuid) FROM resume", preparedStatement -> {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next() ? 0 : resultSet.getInt(1);
        });
    }

    private void addContact(ResultSet resultSet, Resume r) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(resultSet.getString("type")), value);
        }
    }

    private void addSection(ResultSet resultSet, Resume r) throws SQLException {
        String content = resultSet.getString("content");
        if (content!=null){
            SectionType type = SectionType.valueOf(resultSet.getString("type"));
            r.addSection(type, JsonParser.read(content, AbstractSection.class));
        }
    }

    private void insertSections(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, e.getKey().name());
                AbstractSection section = e.getValue();
                preparedStatement.setString(3, JsonParser.write(section, AbstractSection.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertContacts(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, e.getKey().name());
                preparedStatement.setString(3, e.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void deleteContacts(Resume r, Connection connection) throws SQLException {
        deleteSQL(r, connection, "DELETE FROM contact WHERE resume_uuid=?");

    }

    private void deleteSections(Resume r, Connection connection) throws SQLException {
        deleteSQL(r, connection, "DELETE FROM section WHERE resume_uuid=?");
    }

    private void deleteSQL(Resume r, Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.execute();
        }
    }
}
