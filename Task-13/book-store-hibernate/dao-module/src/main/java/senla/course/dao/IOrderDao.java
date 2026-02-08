package senla.course.dao;

import senla.course.entitys.OrderEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IOrderDao {

    OrderEntity findOrderById(Integer id) throws EntityNotFound;

    void createOrder(OrderEntity order) throws EntityNotFound;

    void updateOrdersBeforeAddBook(Integer bookId);

    void updateStatusOrder(int orderId, String statusOrder, String impossibleStatusOrder) throws EntityNotFound;

    List<OrderEntity> getComplectedOrder(LocalDate startDate, LocalDate endDate) throws EntityListEmpty;

    BigDecimal getProfit(LocalDate startDate, LocalDate endDate);

    Long getCountComplectedOrder(LocalDate startDate, LocalDate endDate);

    List<OrderEntity> findAll() throws EntityListEmpty;
}
