package senla.course.model;

import org.springframework.beans.factory.annotation.Autowired;
import senla.course.dao.DaoManager;
import senla.course.enums.StatusOperationOrder;
import senla.course.enums.StatusOrder;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;
import senla.course.mapping.OrderMapping;
import senla.course.mapping.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import senla.course.sorted.order.SortedOrderByDateComplection;
import senla.course.sorted.order.SortedOrderByPrice;
import senla.course.sorted.order.SortedOrderByStatus;
import senla.course.sorted.request.SortedRequestByCountRequest;
import senla.course.sorted.request.SortedRequestByNameBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class OrderManagement {

    private List<Order> orders;
    private List<Request> requests;
    @Value("${orderManagement.possibilityClosedRequest}")
    private boolean possibilityClosedRequest;
    private OrderMapping orderMapper;
    private RequestMapping requestMapping;
    private DaoManager daoManager;

    @Autowired
    public OrderManagement(OrderMapping orderMapper, RequestMapping requestMapping, DaoManager daoManager) {
        this.orderMapper = orderMapper;
        this.requestMapping = requestMapping;
        this.daoManager = daoManager;
    }

    public void initDataFromDataBase() {
        try {
            this.orders = orderMapper.entityListToModelListMapping(daoManager.operationGetAllOrder());
            this.requests = requestMapping.entityListToModelListMapping(daoManager.operationGetAllRequest());
        } catch (EntityListEmpty e) {
            this.orders = new ArrayList<Order>();
            this.requests = new ArrayList<Request>();
        }
    }


    /* Создание заказа на книгу */
    public Order createOrder(int id, int bookId, BigDecimal priceOrder, String emailUser, StatusOrder statusOrder) throws EntityNotFound {
        Order order = new Order(id, bookId, emailUser, priceOrder, statusOrder);
        try {
            daoManager.operationCreateOrder(orderMapper.modelToEntityMapping(order));
        } catch (EntityNotFound e) {
            throw e;
        }
        orders.add(order);
        return order;
    }

    /* Создание заказа на книгу */
    public Order createOrder(int id, int bookId, BigDecimal priceOrder, String emailUser, StatusOrder statusOrder, LocalDate dateComplected) throws EntityNotFound {
        Order order = new Order(id, dateComplected, bookId, emailUser, priceOrder, statusOrder);
        try {
            daoManager.operationCreateOrder(orderMapper.modelToEntityMapping(order));
        } catch (EntityNotFound e) {
            throw e;
        }
        orders.add(order);
        return order;
    }

    /* Поиск заказа по id */
    public Order findOrderById(int id) {
        try {
            Order order = orderMapper.entityToModelMapping(daoManager.operationFindOrderById(id));
            return order;
        } catch (EntityNotFound e) {
            return null;
        }
    }

    /* Отмена заказа на книгу */
    public StatusOperationOrder cancelOrder(int id) {
        try {
            daoManager.operationUpdateStatusOrder(id, "CLOSED", "COMPLECTED");
            return StatusOperationOrder.CLOSED_ORDER;
        } catch (EntityNotFound e) {
            return StatusOperationOrder.ORDER_NOT_FOUND;
        }
    }

    public StatusOperationOrder complectedOrder(int orderId) {
        try {
            daoManager.operationUpdateStatusOrder(orderId, "COMPLECTED", "CLOSED");
            return StatusOperationOrder.COMPLECTED_ORDER;
        } catch (EntityNotFound e) {
            return StatusOperationOrder.ORDER_NOT_FOUND;
        }
    }

    public void updateOrder(int bookId) {
        daoManager.operationUpdateOrdersBeforeAddBook(bookId);
    }

    /* Получить список выполненных заказов за период времени */
    public List<Order> getComplectedOrder(LocalDate startDate, LocalDate endDate) {
        try {
            List<Order> complectedOrder = orderMapper.entityListToModelListMapping(daoManager.operationGetComplectedOrder(startDate, endDate));
            return complectedOrder;
        } catch (EntityListEmpty e) {
            return new ArrayList<Order>();
        }
    }

    /* Получить количество выполненных заказов за период времени */
    public int getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {
        long count = daoManager.operationGetCountComplectedOrder(startDate, endDate);
        return (int) count;
    }

    /* Сумму заработанных средств за период времени */
    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {
        return daoManager.operationGetProfit(startDate, endDate);
    }

    /* Создание запроса на книгу */
    public void createRequest(int requestId, int bookId, String nameBook) {
        int activeRequestId = findActiveRequestByBookId(bookId);
        try {
            if (activeRequestId != -1) {
                daoManager.operationIncrementRequestById(activeRequestId);
                return;
            }
            Request request = new Request(requestId, bookId, nameBook);
                daoManager.operationCreateRequest(requestMapping.modelToEntityMapping(request));
                requests.add(request);
        } catch (EntityNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /* Проверка существует ли действующий запрос на книгу */
    public int findActiveRequestByBookId(int bookId) {
        return daoManager.operationFindActiveRequestOnBook(bookId);
    }

    /* Закрытие запроса на книгу */
    public boolean closedRequest(int requestId) {
        if (possibilityClosedRequest) {
            try {
                daoManager.operationClosedRequestById(requestId);
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
            try {
                daoManager.operationClosedRequestByBookId(bookId);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public List<Order> sortedListOrder(int choiceUser) {
        try {
            List<Order> orders = orderMapper.entityListToModelListMapping(daoManager.operationGetAllOrder());
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
        } catch (EntityListEmpty e) {
            return new ArrayList<Order>();
        }
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
        try {
            List<Request> requests = requestMapping.entityListToModelListMapping(daoManager.operationGetAllRequest());
            switch (choiceUser) {
                case 1:
                    Collections.sort(requests, new SortedRequestByCountRequest());
                    break;
                case 2:
                    Collections.sort(requests, new SortedRequestByNameBook());
                    break;
            }
            return requests;
        } catch (EntityListEmpty e) {
            return new ArrayList<Request>();
        }
    }

    public List<Order> getAllOrder() {
        try {
            return orderMapper.entityListToModelListMapping(daoManager.operationGetAllOrder());
        } catch (EntityListEmpty e) {
            return new ArrayList<Order>();
        }
    }

    public List<Request> getAllRequest() {
        try {
            return requestMapping.entityListToModelListMapping(daoManager.operationGetAllRequest());
        } catch (EntityListEmpty e) {
            return new ArrayList<Request>();
        }
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}

