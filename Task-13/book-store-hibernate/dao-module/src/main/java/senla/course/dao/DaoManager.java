package dao;

import entitys.BookEntity;
import entitys.OrderEntity;
import entitys.RequestEntity;
import exception.EntityListEmpty;
import exception.EntityNotFound;
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

    private final Session session;
    private final IOrderDao orderDao;
    private final IBookDao bookDao;
    private final IRequestDao requestDao;
    private final Logger log = LoggerFactory.getLogger(DaoManager.class);

    public DaoManager(Session session,IOrderDao orderDao, IBookDao bookDao, IRequestDao requestDao) {
        this.session = session;
        this.orderDao = orderDao;
        this.bookDao = bookDao;
        this.requestDao = requestDao;
    }

    public void operationAddBookWarehouse(Integer bookId) throws EntityNotFound {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            orderDao.updateOrdersBeforeAddBook(bookId);
            requestDao.closedRequestByBookId(bookId);
            bookDao.operationBookById(bookId, true);
            transaction.commit();
        } catch (HibernateException | EntityNotFound e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw e;
        }
    }

    public void operationWriteBookWarehouse(Integer bookId) throws EntityNotFound {

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            bookDao.operationBookById(bookId, false);
            transaction.commit();
        } catch (HibernateException | EntityNotFound e) {
            log.error(e.getMessage());
            transaction.rollback();
            throw e;
        }
    }

    public BookEntity operationFindBookById(Integer bookId) throws EntityNotFound {
        try {
            BookEntity book = bookDao.findBookById(bookId);
            return book;
        } catch (HibernateException | EntityNotFound e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<BookEntity> operationGetAllBook() throws EntityListEmpty {
        try {
            List<BookEntity> books = bookDao.findAll();
            return books;
        } catch (EntityListEmpty e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<OrderEntity> operationGetAllOrder() throws EntityListEmpty {
        try {
            List<OrderEntity> orders = orderDao.findAll();
            return orders;
        } catch (EntityListEmpty e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<RequestEntity> operationGetAllRequest() throws EntityListEmpty {
        try {
            List<RequestEntity> requests = requestDao.findAll();
            return requests;
        } catch (EntityListEmpty e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<BookEntity> operationGetStaleBook(Integer countMonthDefineStaleBook) throws EntityListEmpty {
        try {
            List<BookEntity> staleBook = bookDao.getStaleBook(countMonthDefineStaleBook);
            return staleBook;
        } catch (EntityListEmpty e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void operationCreateOrder(OrderEntity order) throws EntityNotFound {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            orderDao.createOrder(order);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.rollback();
        }
    }

    public OrderEntity operationFindOrderById(Integer orderId) throws EntityNotFound {
        try {
            OrderEntity order = orderDao.findOrderById(orderId);
            return order;
        } catch (HibernateException | EntityNotFound e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void operationUpdateStatusOrder(int orderId, String statusOrder, String impossibleStatusOrder) throws EntityNotFound {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            orderDao.updateStatusOrder(orderId, statusOrder, impossibleStatusOrder);
            transaction.commit();
        } catch (EntityNotFound e) {
            log.error(e.getMessage());
            transaction.rollback();
            throw e;
        }
    }

    public void operationUpdateOrdersBeforeAddBook(Integer bookId) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            orderDao.updateOrdersBeforeAddBook(bookId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public List<OrderEntity> operationGetComplectedOrder(LocalDate startDate, LocalDate endDate) throws EntityListEmpty {
        try {
            List<OrderEntity> complectedOrder = orderDao.getComplectedOrder(startDate, endDate);
            return complectedOrder;
        } catch (EntityListEmpty e) {
            log.error(e.getMessage());
            throw e;
        }
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

    public void operationIncrementRequestById(Integer requestId) throws EntityNotFound {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            requestDao.incrementRequestById(requestId);
            transaction.commit();
        } catch (EntityNotFound e) {
            transaction.rollback();
            throw e;
        }
    }

    public void operationClosedRequestById(Integer requestId) throws EntityNotFound {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            requestDao.closedRequestById(requestId);
            transaction.commit();
        } catch (EntityNotFound e) {
            transaction.rollback();
            throw e;
        }
    }

    public void operationClosedRequestByBookId(Integer bookId) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            requestDao.closedRequestByBookId(bookId);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public void operationCreateRequest(RequestEntity request) throws EntityNotFound {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            requestDao.createRequest(request);
            transaction.commit();
        } catch (EntityNotFound e) {
            transaction.rollback();
            throw e;
        }
    }
}
