package senla.course.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.course.dao.DaoManager;
import senla.course.dto.OrderDto;
import senla.course.entitys.OrderEntity;
import senla.course.enums.OrderStatus;
import senla.course.exception.*;
import senla.course.mapper.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private DaoManager daoManager;
    private Mapper<OrderDto, OrderEntity> mapper;

    public OrderService(DaoManager daoManager, Mapper<OrderDto, OrderEntity> mapper) {
        this.daoManager = daoManager;
        this.mapper = mapper;
    }

    public void createOrder(Integer bookId, String emailUser, OrderStatus orderStatus) {

        OrderDto order;

        if (orderStatus == OrderStatus.NEW) {
            order = new OrderDto(bookId, emailUser);
        } else {
            order = new OrderDto(bookId, emailUser, orderStatus);
        }

        daoManager.operationCreateOrder(mapper.mappingDtoToEntity(order));
    }

    public void closedOrder(Integer id) {

        OrderEntity order = daoManager.operationFindOrderById(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }

        daoManager.operationUpdateStatusOrder(id, "CLOSED", "COMPLECTED");
    }

    public void complectedOrder(Integer id) {

        OrderEntity order = daoManager.operationFindOrderById(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }

        Integer bookId = order.getBook().getBookId();

        if (!daoManager.isActiveRequestOnBook(bookId)) {
            daoManager.operationUpdateStatusOrder(id, "COMPLECTED", "CLOSED");
        } else {
            throw new ActiveRequestOnBookException(bookId);
        }

    }

    public OrderDto findOrderById(Integer id) {
        if (id == null || id <= 0) {
            throw new IncorrectIdException(id);
        }
        OrderEntity order = daoManager.operationFindOrderById(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        order.setOrderPrice(order.getBook().getPrice());
        return mapper.mappingEntityToDto(order);
    }

    public List<OrderDto> getAllOrder(String critearia) {
        List<OrderEntity> orders = daoManager.operationGetAllOrder(critearia);
        if (orders.isEmpty()) {
            throw new ListBookEmptyException();
        } else {
            return mapper.mappingEntityListToListDto(orders);
        }
    }

    public List<OrderDto> getComplectedOrder(LocalDate startDate, LocalDate endDate, String criteria) {
        List<OrderEntity> orders = daoManager.operationGetComplectedOrder(startDate, endDate, criteria);

        if (orders.isEmpty()) {
            throw new ListOrderEmptyException();
        }

        return mapper.mappingEntityListToListDto(orders);
    }

    public Long getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {

        return daoManager.operationGetCountComplectedOrder(startDate, endDate);
    }

    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {

        return daoManager.operationGetProfit(startDate, endDate);
    }
}
