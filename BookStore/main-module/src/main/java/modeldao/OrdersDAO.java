package modeldao;

import dao.GenericDAO;
import dao.OperationTransaction;
import di.Inject;
import model.Order;
import status.OrderStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO implements GenericDAO<Order> {

    private final String SQL_GET_BY_ID = "SELECT * FROM Orders WHERE orderID = ?";
    private final String SQL_GET_ALL = "SELECT * FROM Orders";
    private final String SQL_SAVE = "INSERT INTO Orders (customerEmail, orderStatus, dateOfExecution, bookID) VALUES (?, ?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE Orders SET customerEmail = ?, orderStatus = ?, dateOfExecution = ?, bookID = ? WHERE orderID = ?";
    private final String SQL_DELETE_BY_SQL = "DELETE FROM Orders WHERE orderID = ?";

    @Inject
    private Connection connection;

    public OrdersDAO() {
    }

    public OrdersDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Order getByID(int id) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Order order = null;
            if (rs.next()) {
                Integer orderID = rs.getInt("orderID");
                String customerEmail = rs.getString("customerEmail");
                OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("orderStatus"));
                Integer bookID = rs.getInt("bookID");
                LocalDate dateOfExecution = rs.getDate("dateOfExecution").toLocalDate();
                order = new Order(customerEmail, dateOfExecution, bookID, orderStatus);
                order.setOrderID(orderID);
            }
            return order;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<Order>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                Integer orderID = rs.getInt("orderID");
                String customerEmail = rs.getString("customerEmail");
                OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("orderStatus"));
                Integer bookID = rs.getInt("bookID");
                LocalDate dateOfExecution = rs.getDate("dateOfExecution").toLocalDate();
                Order order = new Order(customerEmail, dateOfExecution, bookID, orderStatus);
                order.setOrderID(orderID);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Order entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
            statement.setString(1, entity.getCustomerEmail());
            statement.setObject(2, (entity.getOrderStatus()), Types.OTHER);
            statement.setDate(3, Date.valueOf(entity.getDateOfExecution()));
            statement.setInt(4, entity.getBookID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order entity) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getCustomerEmail());
            statement.setObject(2, (entity.getOrderStatus()), Types.OTHER);
            statement.setDate(3, Date.valueOf(entity.getDateOfExecution()));
            statement.setInt(4, entity.getBookID());
            statement.setInt(5, entity.getOrderID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Order entity) {
        int id = entity.getOrderID();
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
