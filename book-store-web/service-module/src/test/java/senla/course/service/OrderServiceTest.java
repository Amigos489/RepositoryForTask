package senla.course.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import senla.course.dao.DaoManager;
import senla.course.dto.OrderDto;
import senla.course.entitys.BookEntity;
import senla.course.entitys.OrderEntity;
import senla.course.exception.IncorrectIdException;
import senla.course.exception.ListOrderEmptyException;
import senla.course.exception.OrderNotFoundException;
import senla.course.mapper.OrderMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@DisplayName("Test OrderService")
public class OrderServiceTest {

    static OrderMapper mapper;

    @BeforeAll
    static void createOrderMapper() {
        mapper = new OrderMapper();
    }

    @Test
    @DisplayName("Given closed order When correct order id Then not throw exception")
    void closedOrder_correctOrderId_notThrowException() {

        int id = 2;

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationFindOrderById(2)).thenReturn(new OrderEntity());

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertDoesNotThrow(() -> orderService.closedOrder(id));
    }

    @Test
    @DisplayName("Given closed order When order with specified id not found Then throw exception OrderNotFoundException")
    void closedOrder_orderWithSpecifiedIdNotFound_throwOrderNotFoundException() {

        int id = 999;

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationFindOrderById(999)).thenReturn(null);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertThrows(OrderNotFoundException.class,() -> orderService.closedOrder(id));
    }

    @Test
    @DisplayName("Given complected order When correct order id Then not throw exception")
    void complectedOrder_correctOrderId_notThrowException() {

        int id = 2;

        OrderEntity orderForClosed = new OrderEntity();
        BookEntity bookForTest = new BookEntity();
        orderForClosed.setBook(bookForTest);

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationFindOrderById(2)).thenReturn(orderForClosed);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertDoesNotThrow(() -> orderService.complectedOrder(id));
    }

    @Test
    @DisplayName("Given complected order When order with specified id not found Then throw exception OrderNotFoundException")
    void complectedOrder_orderWithSpecifiedIdNotFound_throwOrderNotFoundException() {

        int id = 999;

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationFindOrderById(999)).thenReturn(null);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertThrows(OrderNotFoundException.class,() -> orderService.complectedOrder(id));
    }

    @Test
    @DisplayName("Given find order by id When correct order id Then get orderDto")
    void findOrderById_correctOrderId_getOrder() {

        int id = 1;

        OrderEntity orderEntityForTest = new OrderEntity();
        orderEntityForTest.setOrderId(1);
        orderEntityForTest.setStatusOrder("NEW");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookId(1);
        orderEntityForTest.setBook(bookEntity);

        OrderDto orderDtoForTest = mapper.mappingEntityToDto(orderEntityForTest);

        DaoManager daoManager = Mockito.mock(DaoManager.class);
        when(daoManager.operationFindOrderById(1)).thenReturn(orderEntityForTest);

        OrderService orderService = new OrderService(daoManager, mapper);
        OrderDto actualOrderDto = orderService.findOrderById(id);

        Assertions.assertEquals(orderDtoForTest.getId(), actualOrderDto.getId());
    }

    @Test
    @DisplayName("Given find order by id When order with specified id not found Then throw exception OrderNotFoundException")
    void findOrderById_orderWithSpecifiedIdNotFound_throwOrderNotFoundException() {

        int id = 999;

        DaoManager daoManager = Mockito.mock(DaoManager.class);
        when(daoManager.operationFindOrderById(999)).thenThrow(OrderNotFoundException.class);

        OrderService orderService = new OrderService(daoManager, mapper);

        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(id));
    }

    @Test
    @DisplayName("Given find order by id When incorrect order id Then throw exception IncorrectIdException")
    void findOrderById_incorrectOrderId_throwIncorrectIdException() {

        Integer firstIncorrectId = null;
        Integer secondIncorrectId = -1;

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationFindOrderById(Mockito.argThat(arg -> arg == null || arg < 0))).thenThrow(IncorrectIdException.class);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertThrows(IncorrectIdException.class, () -> orderService.findOrderById(firstIncorrectId));
        Assertions.assertThrows(IncorrectIdException.class, () -> orderService.findOrderById(secondIncorrectId));
    }

    @Test
    @DisplayName("Given get all orders When list order not empty Then get list order")
    void getAllOrder_ListOrderNotEmpty_getListOrder() {

        BookEntity book = new BookEntity();
        book.setBookId(1);

        List<OrderEntity> orderEntityListForTest = new ArrayList<>();

        OrderEntity order1 = new OrderEntity();
        order1.setOrderId(1);
        order1.setStatusOrder("NEW");
        order1.setBook(book);
        orderEntityListForTest.add(order1);

        OrderEntity order2 = new OrderEntity();
        order2.setOrderId(2);
        order2.setStatusOrder("NEW");
        order2.setBook(book);
        orderEntityListForTest.add(order2);

        List<OrderDto> orderDtoListForTest = new ArrayList<>();
        orderDtoListForTest.add(mapper.mappingEntityToDto(order1));
        orderDtoListForTest.add(mapper.mappingEntityToDto(order2));

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetAllOrder("CriteriaTest")).thenReturn(orderEntityListForTest);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        List<OrderDto> orderDtoListActual = orderService.getAllOrder("CriteriaTest");

        Assertions.assertEquals(orderDtoListForTest.size(), orderDtoListActual.size());

        for (int i = 0; i < orderDtoListActual.size(); i++) {
            Assertions.assertEquals(orderDtoListActual.get(i).getId(), orderDtoListActual.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get all order When list order empty Then throw exception ListOrderEmptyException")
    void getAllOrder_ListOrderEmpty_throwListOrderEmptyException() {

        List<OrderEntity> ordersEntity = new ArrayList<>();

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetAllOrder("CriteriaTest")).thenReturn(ordersEntity);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertThrows(ListOrderEmptyException.class, () -> orderService.getAllOrder("CriteriaTest"));
    }

    @Test
    @DisplayName("Given get complected order When list complected order not empty Then get list complected order")
    void getComplectedOrder_ListComplectedOrderNotEmpty_getListOrder() {

        BookEntity book = new BookEntity();
        book.setBookId(1);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        List<OrderEntity> complectedOrdersEntity = new ArrayList<>();

        OrderEntity complectedOrder1 = new OrderEntity();
        complectedOrder1.setOrderId(1);
        complectedOrder1.setStatusOrder("NEW");
        complectedOrder1.setBook(book);
        complectedOrdersEntity.add(complectedOrder1);

        OrderEntity complectedOrder2 = new OrderEntity();
        complectedOrder2.setOrderId(2);
        complectedOrder2.setStatusOrder("NEW");
        complectedOrder2.setBook(book);
        complectedOrdersEntity.add(complectedOrder2);

        List<OrderDto> complectedOrderDtoListForTest = new ArrayList<>();
        complectedOrderDtoListForTest.add(mapper.mappingEntityToDto(complectedOrder1));
        complectedOrderDtoListForTest.add(mapper.mappingEntityToDto(complectedOrder2));

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetComplectedOrder(LocalDate.now(), LocalDate.now().plusDays(7), "CriteriaTest"))
                .thenReturn(complectedOrdersEntity);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        List<OrderDto> ordersDtoActual = orderService.getComplectedOrder(startDate, endDate, "CriteriaTest");

        Assertions.assertEquals(complectedOrderDtoListForTest.size(), ordersDtoActual.size());

        for (int i = 0; i < ordersDtoActual.size(); i++) {
            Assertions.assertEquals(ordersDtoActual.get(i).getId(), ordersDtoActual.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get complected order When list complected order empty Then throw exception ListOrderEmptyException")
    void getComplectedOrder_ListComplectedOrderEmpty_throwListOrderEmptyException() {

        List<OrderEntity> complectedOrdersEntity = new ArrayList<>();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager
                .operationGetComplectedOrder(LocalDate.now(), LocalDate.now().minusMonths(1), "CriteriaTest"))
                    .thenReturn(complectedOrdersEntity);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertThrows(ListOrderEmptyException.class, () -> orderService.getComplectedOrder(startDate, endDate, "CriteriaTest"));
    }

    @Test
    @DisplayName("Given get count complected order When list complected order not empty Then get count complected order")
    void getCountComplectedOrder_ListComplectedOrderNotEmpty_getCountComplectedOrder() {

        Long countComplectedOrder = 7L;

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetCountComplectedOrder(LocalDate.now(), LocalDate.now().minusMonths(1))).thenReturn(7L);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertEquals(countComplectedOrder, orderService.getCountComplectedOrder(LocalDate.now(), LocalDate.now().minusMonths(1)));
    }

    @Test
    @DisplayName("Given get count complected order When list complected order empty Then get zero")
    void getCountComplectedOrder_ListComplectedOrderEmpty_getZero() {

        Long countComplectedOrder = 0L;

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetCountComplectedOrder(LocalDate.now(), LocalDate.now().minusMonths(1))).thenReturn(0L);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertEquals(countComplectedOrder, orderService.getCountComplectedOrder(LocalDate.now(), LocalDate.now().plusMonths(1)));
    }

    @Test
    @DisplayName("Given get profit When list complected order not empty Then get profit")
    void getProfit_ListComplectedOrderNotEmpty_getProfit() {

        BigDecimal profit = BigDecimal.valueOf(1000);

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetProfit(LocalDate.now(), LocalDate.now().plusMonths(1))).thenReturn(profit);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertEquals(profit, orderService.getProfit(LocalDate.now(), LocalDate.now().plusMonths(1)));
    }

    @Test
    @DisplayName("Given get profit When list complected order empty Then get zero")
    void getProfit_ListComplectedOrderEmpty_getZero() {

        BigDecimal zeroProfit = BigDecimal.ZERO;

        DaoManager mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetProfit(LocalDate.now(), LocalDate.now().plusMonths(1))).thenReturn(zeroProfit);

        OrderService orderService = new OrderService(mockedDaoManager, mapper);

        Assertions.assertEquals(zeroProfit, orderService.getProfit(LocalDate.now(), LocalDate.now().plusMonths(1)));
    }
}
