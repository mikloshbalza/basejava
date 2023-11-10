package com.basejava.webapp.util;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T connect(String sql, SqlExecutor<T> sqlExecutor) {
        try (Connection connection = this.connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return sqlExecutor.execute(preparedStatement);
        } catch (SQLException e) {
            //System.out.println(e.getSQLState());
            if (e.getSQLState().equals("23505")){
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }
}
