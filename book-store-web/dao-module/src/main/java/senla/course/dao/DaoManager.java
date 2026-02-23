package senla.course.dao;

import org.hibernate.SessionFactory;
import senla.course.entitys.BookEntity;
import senla.course.entitys.OrderEntity;
import senla.course.entitys.RequestEntity;
import senla.course.exception.EntityListEmpty;
import senla.course.exception.EntityNotFound;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class DaoManager {

    private final IOrderDao orderDao;
    private final IBookDao bookDao;
    private final IRequestDao requestDao;
    private final Logger log = LoggerFactory.getLogger(DaoManager.class);

    public DaoManager(IOrderDao orderDao, IBookDao bookDao, IRequestDao requestDao) {
        this.orderDao = orderDao;
        this.bookDao = bookDao;
        this.requestDao = requestDao;
    }

    public void operationAddBookWarehouse(Integer bookId) {
        orderDao.updateOrdersBeforeAddBook(bookId);
        requestDao.closedRequestByBookId(bookId);
        bookDao.operationBookById(bookId, true);
    }

    public void operationWriteBookWarehouse(Integer bookId) {
        bookDao.operationBookById(bookId, false);
    }

    public BookEntity operationFindBookById(Integer bookId) {
        return bookDao.findBookById(bookId);
    }

    public List<BookEntity> operationGetAllBook(String criteria) {
        return bookDao.findAll(criteria);
    }

    public List<OrderEntity> operationGetAllOrder(String criteria) {
        return orderDao.findAll(criteria);
    }

    public List<RequestEntity> operationGetAllRequest(String critearia) {
        return requestDao.findAll(critearia);
    }

    public List<BookEntity> operationGetStaleBook(Integer countMonthDefineStaleBook, String criteria) {
        return bookDao.getStaleBook(countMonthDefineStaleBook, criteria);
    }

    public void operationCreateOrder(OrderEntity order) {
        orderDao.createOrder(order);
    }

    public OrderEntity operationFindOrderById(Integer orderId) {
        return orderDao.findOrderById(orderId);
    }

    public void operationUpdateStatusOrder(int orderId, String statusOrder, String impossibleStatusOrder) {
        orderDao.updateStatusOrder(orderId, statusOrder, impossibleStatusOrder);
    }

    public void operationUpdateOrdersBeforeAddBook(Integer bookId) {
        orderDao.updateOrdersBeforeAddBook(bookId);
    }

    public List<OrderEntity> operationGetComplectedOrder(LocalDate startDate, LocalDate endDate, String criteria) {
        return orderDao.getComplectedOrder(startDate, endDate, criteria);
    }

    public Long operationGetCountComplectedOrder(LocalDate startDate, LocalDate endDate) {
        return orderDao.getCountComplectedOrder(startDate, endDate);
    }

    public BigDecimal operationGetProfit(LocalDate startDate, LocalDate endDate) {
        return orderDao.getProfit(startDate, endDate);
    }

    public Integer operationFindActiveRequestOnBook(Integer bookId) {
        return requestDao.findActiveRequestOnBook(bookId);
    }

    public void operationIncrementRequestById(Integer requestId) {
        requestDao.incrementRequestById(requestId);
    }

    public void operationClosedRequestById(Integer requestId) {
        requestDao.closedRequestById(requestId);
    }

    public void operationClosedRequestByBookId(Integer bookId) {
        requestDao.closedRequestByBookId(bookId);
    }

    public void operationCreateRequest(RequestEntity request) {
        requestDao.createRequest(request);
    }

    public boolean isActiveRequestOnBook(Integer bookId) {
        if (requestDao.findActiveRequestOnBook(bookId) == -1) {
            return false;
        } else {
            return true;
        }
    }
}
