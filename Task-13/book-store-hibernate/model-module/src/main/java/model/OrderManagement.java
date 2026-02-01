package model;

import annotations.ConfigProperty;
import dao.OrderDao;
import dao.RequestDao;
import enums.StatusOperationOrder;
import enums.StatusOrder;
import exception.EntityNotFound;
import mapping.OrderMapping;
import mapping.RequestMapping;
import sorted.order.SortedOrderByDateComplection;
import sorted.order.SortedOrderByPrice;
import sorted.order.SortedOrderByStatus;
import sorted.request.SortedRequestByCountRequest;
import sorted.request.SortedRequestByNameBook;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderManagement {

    private List<Order> orders;
    private List<Request> requests;
    @ConfigProperty(type=Boolean.class)
    private boolean possibilityClosedRequest;
    private OrderDao orderDao;
    private OrderMapping orderMapper;
    private RequestDao requestDao;
    private RequestMapping requestMapping;


    public OrderManagement() {}

    public OrderManagement(OrderDao orderDao, OrderMapping orderMapper, RequestDao requestDao, RequestMapping requestMapping) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
        this.requestDao = requestDao;
        this.requestMapping = requestMapping;
    }

    public void initDataFromDataBase() {
        this.orders = orderMapper.entityListToModelListMapping(orderDao.findAll());
        this.requests = requestMapping.entityListToModelListMapping(requestDao.findAll());
    }


    /* Создание заказа на книгу */
    public Order createOrder(int id, int bookId, BigDecimal priceOrder, String emailUser, StatusOrder statusOrder) throws EntityNotFound {
        Order order = new Order(id, bookId, emailUser ,priceOrder, statusOrder);
        try {
            orderDao.createOrder(orderMapper.modelToEntityMapping(order));
        } catch (EntityNotFound e) {
            throw e;
        }
        orders.add(order);
        return order;
    }

    /* Поиск заказа по id */
    public Order findOrderById(int id) {
        try {
            Order order = orderMapper.entityToModelMapping(orderDao.findOrderById(id));
            return order;
        } catch (EntityNotFound e) {
            return null;
        }
    }

    /* Отмена заказа на книгу */
    public StatusOperationOrder cancelOrder(int id) {
        try {
            orderDao.updateStatusOrder(id, "CLOSED", "COMPLECTED");
            return StatusOperationOrder.CLOSED_ORDER;
        } catch (EntityNotFound e) {
            return StatusOperationOrder.ORDER_NOT_FOUND;
        }
    }

    public StatusOperationOrder complectedOrder(int orderId) {
        try {
            orderDao.updateStatusOrder(orderId, "COMPLECTED", "CLOSED");
            return StatusOperationOrder.COMPLECTED_ORDER;
        } catch (EntityNotFound e) {
            return StatusOperationOrder.ORDER_NOT_FOUND;
        }
    }

    public void updateOrder(int bookId) {
        orderDao.updateOrdersBeforeAddBook(bookId);
    }

    /* Получить список выполненных заказов за период времени */
    public List<Order> getComplectedOrder(LocalDate startDate, LocalDate endDate) {
        List<Order> complectedOrder =  orderMapper.entityListToModelListMapping(orderDao.getComplectedOrder(startDate, endDate));
        return complectedOrder;
    }

    /* Получить количество выполненных заказов за период времени */
    public int getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {
        long count = orderDao.getCountComplectedOrder(startDate, endDate);
        int countInt = (int) count;
        return countInt;
    }

    /* Сумму заработанных средств за период времени */
    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {
        return orderDao.getProfit(startDate, endDate);
    }

    /* Создание запроса на книгу */
    public void createRequest(int requestId, int bookId, String nameBook) {
        int activeRequestId = findActiveRequestByBookId(bookId);
        System.out.println("Активный запрос на книгу: " + activeRequestId);
        if (activeRequestId != -1) {
            requestDao.incrementRequestById(activeRequestId);
            return;
        }
        Request request = new Request(requestId, bookId, nameBook);
        try {
            requestDao.createRequest(requestMapping.modelToEntityMapping(request));
            requests.add(request);
        } catch (EntityNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /* Проверка существует ли действующий запрос на книгу */
    public int findActiveRequestByBookId(int bookId) {
        return requestDao.findActiveRequestOnBook(bookId);
    }

    /* Закрытие запроса на книгу */
    public boolean closedRequest(int requestId) {
        if (possibilityClosedRequest) {
            try {
                requestDao.closedRequestById(requestId);
                return true;
            } catch (EntityNotFound e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    /* Закрытие запроса на книгу */
    public boolean closedRequestByBookId(int bookId) {
        if (possibilityClosedRequest) {
            requestDao.closedRequestByBookId(bookId);
            return true;
        }
        return false;
    }

    public List<Order> sortedListOrder(int choiceUser) {
        List<Order> orders = orderMapper.entityListToModelListMapping(orderDao.findAll());
        switch (choiceUser) {
            case 1:
                Collections.sort(orders, new SortedOrderByDateComplection());
                break;
            case 2:
                Collections.sort(orders, new SortedOrderByPrice());
                break;
            case 3:
                Collections.sort(orders, new SortedOrderByStatus());
                break;
        }
        return orders;
    }

    public List<Order> sortedListComplectedOrder(int choiceUser, LocalDate startDate, LocalDate endDate) {
        List<Order> complectedOrder = getComplectedOrder(startDate, endDate);
        switch (choiceUser) {
            case 1:
                Collections.sort(complectedOrder, new SortedOrderByDateComplection());
                break;
            case 2:
                Collections.sort(complectedOrder, new SortedOrderByPrice());
                break;
        }
        return complectedOrder;
    }

    public List<Request> sortedListRequest(int choiceUser) {
        List<Request> requests = requestMapping.entityListToModelListMapping(requestDao.findAll());
        switch (choiceUser) {
            case 1:
                Collections.sort(requests, new SortedRequestByCountRequest());
                break;
            case 2:
                Collections.sort(requests, new SortedRequestByNameBook());
                break;
        }
        return requests;
    }

    public List<Order> getAllOrder() {
        return orderMapper.entityListToModelListMapping(orderDao.findAll());
    }

    public List<Request> getAllRequest() {
        return requestMapping.entityListToModelListMapping(requestDao.findAll());
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
