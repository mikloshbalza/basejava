package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.connect("DELETE FROM resume", PreparedStatement::execute);
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM resume")) {
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void save(Resume r) {
        sqlHelper.connect("INSERT INTO resume (uuid, full_name) VALUES (?,?)", preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
            return null;
        });
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
//            preparedStatement.setString(1, r.getUuid());
//            preparedStatement.setString(2, r.getFullName());
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            throw new ExistStorageException(r.getUuid(), e);
//        }
    }

    @Override
    public void update(Resume r) {
        sqlHelper.connect("UPDATE resume SET full_name=? WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            preparedStatement.execute();
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
//            preparedStatement.setString(1, r.getFullName());
//            preparedStatement.setString(2, r.getUuid());
//            preparedStatement.execute();
//            if (preparedStatement.executeUpdate()==0){
//                throw new NotExistStorageException(r.getUuid());
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public Resume get(String uuid) {
       return sqlHelper.connect("SELECT * FROM resume r WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
           return new Resume(uuid, resultSet.getString("full_name"));
        });
/*        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, resultSet.getString("full_name"));
            return r;
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.connect("DELETE FROM resume WHERE uuid=?",preparedStatement -> {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
        /*try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public List<Resume> getAllSorted() {
       return sqlHelper.connect("SELECT * FROM resume ORDER BY full_name,uuid",preparedStatement -> {
           preparedStatement.execute();
           ResultSet resultSet = preparedStatement.executeQuery();
           List<Resume> resumes = new ArrayList<>();
           while (resultSet.next()) {
               resumes.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name").trim()));
           }
           return resumes;
       });
       /* try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name,uuid")) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name").trim()));
            }
            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public int size() {
        return sqlHelper.connect("SELECT COUNT(uuid) FROM resume",preparedStatement -> {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt(1);
        });
        /*try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(uuid) FROM resume")) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

    }
}
