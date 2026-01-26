package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionDataBase {

    private static ConnectionDataBase instance;
    private Connection connection;
    private static final Logger logger = LogManager.getLogger(ConnectionDataBase.class);

    private ConnectionDataBase() {
    }

    public static ConnectionDataBase getInstance() {
        if (instance == null) {
            instance = new ConnectionDataBase();
        }
        return instance;
    }

    public Connection openConnection(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Установлено соединение с бд.");
            return connection;
        } catch (SQLException e) {
            logger.error("Не удалось подключиться к бд.");
            e.printStackTrace();
            return null;
        }
    }
    public void closeConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                return;
            }
            connection.close();
            logger.info("Соединение с бд закрыто.");
        } catch (SQLException e) {
            logger.error("Не удалось закрыть соединение с бд.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}