import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksDAO implements GenericDAO<Book> {

    private final String SQL_GET_BY_ID = "SELECT * FROM Books WHERE bookID = ?";
    private final String SQL_GET_ALL = "SELECT * FROM Books";
    private final String SQL_SAVE = "INSERT INTO Books (nameBook, authorBook, dateOfPublication, dateAddedToWarehouse, numberOfCopies, numberOfPages, numberOfRequests, price, availability) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Books SET nameBook = ?, authorBook = ?, dateOfPublication = ?, dateAddedToWarehouse = ?, numberOfCopies = ?, numberOfPages = ?, numberOfRequests = ?, price = ?, availability = ? WHERE bookID = ?";
    private final String SQL_DELETE_BY_SQL = "DELETE FROM books WHERE bookId = ?";

    @Inject
    private Connection connection;

    public BooksDAO() {}

    public BooksDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Book getByID(int id) {
        Book book = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_ID);) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Integer bookId = rs.getInt("bookId");
                String nameBook = rs.getString("nameBook");
                String authorBook = rs.getString("authorBook");
                LocalDate dateOfPublication = rs.getDate("dateOfPublication").toLocalDate();
                LocalDate dateAddedToWarehouse = rs.getDate("dateAddedToWarehouse").toLocalDate();
                Integer numberOfCopies = rs.getInt("numberOfCopies");
                Integer numberOfPages = rs.getInt("numberOfPages");
                Integer numberOfRequests = rs.getInt("numberOfRequests");

                String priceString = rs.getString("price");
                if (priceString != null) {
                    priceString = replacePrice(priceString);
                }
                book = new Book(nameBook, authorBook, dateOfPublication, numberOfCopies, numberOfPages, new BigDecimal(priceString), dateAddedToWarehouse, numberOfRequests);
                book.setBookID(bookId);
            }
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<Book>();
        try (Statement statement = connection.createStatement();) {
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            while(rs.next()) {
                Integer bookId = rs.getInt("bookId");
                String nameBook = rs.getString("nameBook");
                String authorBook = rs.getString("authorBook");
                LocalDate dateOfPublication = rs.getDate("dateOfPublication").toLocalDate();
                LocalDate dateAddedToWarehouse = rs.getDate("dateAddedToWarehouse").toLocalDate();
                Integer numberOfCopies = rs.getInt("numberOfCopies");
                Integer numberOfPages = rs.getInt("numberOfPages");
                Integer numberOfRequests = rs.getInt("numberOfRequests");
                String priceString = rs.getString("price");
                if (priceString != null) {
                    priceString = replacePrice(priceString);
                }
                Book book = new Book(nameBook, authorBook, dateOfPublication, numberOfCopies, numberOfPages, new BigDecimal(priceString), dateAddedToWarehouse, numberOfRequests);
                book.setBookID(bookId);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Book book) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) { 
            statement.setString(1, book.getNameBook());
            statement.setString(2, book.getAuthorBook());
            statement.setDate(3, Date.valueOf(book.getDateOfPublication()));
            statement.setDate(4, Date.valueOf(book.getDateAddedToWarehouse()));
            statement.setInt(5, book.getNumberOfCopies());
            statement.setInt(6, book.getNumberPages());
            statement.setInt(7, book.getNumberOfRequests());
            statement.setBigDecimal(8, book.getPrice());
            statement.setBoolean(9, book.getAvailability());
            System.out.println(statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void update(Book book) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, book.getNameBook());
            statement.setString(2, book.getAuthorBook());
            statement.setDate(3, Date.valueOf(book.getDateOfPublication()));
            statement.setDate(4, Date.valueOf(book.getDateAddedToWarehouse()));
            statement.setInt(5, book.getNumberOfCopies());
            statement.setInt(6, book.getNumberPages());
            statement.setInt(7, book.getNumberOfRequests());
            statement.setBigDecimal(8, book.getPrice());
            statement.setBoolean(9, book.getAvailability());
            statement.setInt(10, book.getBookID());
            System.out.println(statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void delete(Book book) {
        int id = book.getBookID();
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

    public String replacePrice(String price) {
        price = price.replace(',', '.');
        price = price.replace('?', ' ');
        price = price.trim();
        return price;
    }

    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollbackTransaction() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void accomplishmentTransaction(OperationTransaction operation) {
        try {
            switch (operation) {
                case START: {
                    connection.setAutoCommit(false);
                    break;
                }
                case COMMIT: {
                    connection.commit();;
                    break;
                }
                case ROLLBACK: {
                    connection.rollback();;
                    break;
                }
                case END: {
                    connection.setAutoCommit(true);
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}