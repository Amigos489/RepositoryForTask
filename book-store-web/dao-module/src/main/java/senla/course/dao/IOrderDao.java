package senla.course.dao;

import senla.course.entitys.OrderEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IOrderDao {

    OrderEntity findOrderById(Integer id);

    void createOrder(OrderEntity order);

    void updateOrdersBeforeAddBook(Integer bookId);

    void updateStatusOrder(int orderId, String statusOrder, String impossibleStatusOrder);

    List<OrderEntity> getComplectedOrder(LocalDate startDate, LocalDate endDate, String criteria);

    BigDecimal getProfit(LocalDate startDate, LocalDate endDate);

    Long getCountComplectedOrder(LocalDate startDate, LocalDate endDate);

    List<OrderEntity> findAll(String criteria);
}
