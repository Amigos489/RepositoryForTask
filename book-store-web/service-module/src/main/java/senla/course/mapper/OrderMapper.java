package senla.course.mapper;

import org.springframework.stereotype.Component;
import senla.course.dto.BookDto;
import senla.course.dto.OrderDto;
import senla.course.entitys.BookEntity;
import senla.course.entitys.OrderEntity;
import senla.course.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper extends Mapper<OrderDto, OrderEntity> {

    @Override
    public OrderDto mappingEntityToDto(OrderEntity entity) {
        int orderId = entity.getOrderId();
        LocalDate dateComplection = entity.getDateComplection();
        int bookId = entity.getBook().getBookId();
        String emailUser = entity.getEmailUser();
        BigDecimal orderPrice = entity.getOrderPrice();
        OrderStatus statusOrder = Enum.valueOf(OrderStatus.class, entity.getStatusOrder());
        return new OrderDto(orderId, dateComplection, bookId, emailUser, orderPrice, statusOrder);
    }

    @Override
    public OrderEntity mappingDtoToEntity(OrderDto dto) {
        LocalDate dateComplection = dto.getDateComplection();
        String emailUser = dto.getEmailUser();
        String statusOrder = String.valueOf(dto.getStatusOrder());
        Integer bookId = dto.getBookId();
        return new OrderEntity(dateComplection, emailUser, statusOrder, bookId);
    }

    @Override
    public List<OrderDto> mappingEntityListToListDto(List<OrderEntity> entitys) {
        List<OrderDto> ordersDto = new ArrayList<OrderDto>();
        for (OrderEntity entity : entitys) {
            ordersDto.add(mappingEntityToDto(entity));
        }
        return ordersDto;
    }
}
