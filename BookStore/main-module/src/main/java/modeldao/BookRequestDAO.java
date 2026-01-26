package modeldao;

import dao.GenericDAO;
import dao.OperationTransaction;
import di.Inject;
import model.BookRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRequestDAO implements GenericDAO<BookRequest> {

    private final String SQL_GET_BY_ID = "SELECT * FROM Requests WHERE requestID = ?";
    private final String SQL_GET_ALL = "SELECT * FROM Requests";
    private final String SQL_SAVE = "INSERT INTO Requests (bookID, requestCount, fulfilled) VALUES (?, ?, ?)";
    private final String SQL_UPDATE = "INSERT INTO Requests (bookID, requestCount, fulfilled) VALUES (?, ?, ?)";
    private final String SQL_DELETE_BY_SQL = "DELETE FROM Requests WHERE requestID = ?";

    @Inject
    private Connection connection;

    public BookRequestDAO() {
    }

    public BookRequestDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public BookRequest getByID(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            BookRequest bookRequest = null;
            if (rs.next()) {
                Integer requestID = rs.getInt("requestID");
                Integer bookID = rs.getInt("bookID");
                Integer requestCount = rs.getInt("requestCount");
                boolean fulfilled = rs.getBoolean("fulfilled");
                bookRequest = new BookRequest(bookID, requestCount, fulfilled);
                bookRequest.setId(requestID);
            }
            return bookRequest;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BookRequest> getAll() {
        List<BookRequest> requests = new ArrayList<BookRequest>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            BookRequest bookRequest = null;
            while (rs.next()) {
                Integer requestID = rs.getInt("requestID");
                Integer bookID = rs.getInt("bookID");
                Integer requestCount = rs.getInt("requestCount");
                boolean fulfilled = rs.getBoolean("fulfilled");
                bookRequest = new BookRequest(bookID, requestCount, fulfilled);
                bookRequest.setId(requestID);
                requests.add(bookRequest);
            }
            return requests;
        } catch (SQLException e) {
            e.printStackTrace();
            return requests;
        }
    }

    @Override
    public void save(BookRequest entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
            statement.setInt(1, entity.getBookId());
            statement.setInt(2, entity.getRequestCount());
            statement.setBoolean(3, entity.isFulfilled());
            System.out.println(statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void update(BookRequest entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, entity.getBookId());
            statement.setInt(2, entity.getRequestCount());
            statement.setBoolean(3, entity.isFulfilled());
            statement.setInt(4, entity.getId());
            System.out.println(statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void delete(BookRequest entity) {
        int id = entity.getId();
        deleteByID(id);
    }

    @Override
    public void deleteByID(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_SQL)) {
            statement.setInt(1, id);
            System.out.println(statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void accomplishmentTransaction(OperationTransaction operation) {
        try {
            switch (operation) {
                case START:
                    connection.setAutoCommit(false);
                    break;
                case COMMIT:
                    connection.commit();
                    break;
                case ROLLBACK:
                    connection.rollback();
                    break;
                case END:
                    connection.setAutoCommit(true);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
