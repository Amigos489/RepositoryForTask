package senla.course.mapping;

import org.springframework.stereotype.Component;
import senla.course.entitys.OrderEntity;
import senla.course.enums.StatusOrder;
import senla.course.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class OrderMapping extends AbstractMapper<Order, OrderEntity> {

    @Override
    public Order entityToModelMapping(OrderEntity orderEntity) {
        int orderId = orderEntity.getOrderId();
        LocalDate dateComplection = orderEntity.getDateComplection();
        int bookId = orderEntity.getBook().getBookId();
        String emailUser = orderEntity.getEmailUser();
        BigDecimal orderPrice = orderEntity.getOrderPrice();
        StatusOrder statusOrder = Enum.valueOf(StatusOrder.class, orderEntity.getStatusOrder());
        return new Order(orderId, dateComplection, bookId, emailUser, orderPrice, statusOrder);
    }

    @Override
    public OrderEntity modelToEntityMapping(Order model) {
        LocalDate dateComplection = model.getDateComplection();
        String emailUser = model.getEmailUser();
        String statusOrder = String.valueOf(model.getStatusOrder());
        Integer bookId = model.getBookId();
        return new OrderEntity(dateComplection, emailUser, statusOrder, bookId);
    }
}
