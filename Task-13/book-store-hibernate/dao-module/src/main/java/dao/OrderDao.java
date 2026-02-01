package dao;

import entitys.BookEntity;
import entitys.OrderEntity;
import exception.EntityNotFound;
import exception.QueryInvalidResult;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderDao extends HibernateAbstractDao<OrderEntity, Integer>{

    private final Logger log = LoggerFactory.getLogger(OrderDao.class);
    private final Session session;

    public OrderDao(Session session) {
        this.session = session;
    }

    public OrderEntity findOrderById(Integer id) throws EntityNotFound {
        try {
            session.beginTransaction();
            OrderEntity orderEntity = session.find(OrderEntity.class, id);
            if (orderEntity == null) {
                throw new EntityNotFound(super.messageOrderNotFound);
            }
            orderEntity.setOrderPrice(orderEntity.getBook().getPrice());
            session.getTransaction().commit();
            return orderEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<OrderEntity> findAll() {
        List<OrderEntity> orders = session.createQuery("FROM OrderEntity", OrderEntity.class).getResultList();
        List<BookEntity> books = session.createQuery("FROM BookEntity", BookEntity.class).getResultList();

        for (OrderEntity order : orders) {
            for (BookEntity book : books) {
                if (order.getBook().getBookId().equals(book.getBookId())) {
                    order.setOrderPrice(book.getPrice());
                }
            }
        }

        return orders;
    }

    public void createOrder(OrderEntity order) throws EntityNotFound {
        try {
            session.beginTransaction();
            BookEntity book = session.find(BookEntity.class, order.getBookId());
            if (book == null) {
                throw new EntityNotFound(super.messageBookNotFound);
            }
            order.setBook(book);
            order.setOrderPrice(book.getPrice());
            session.save(order);
            session.flush();
            session.getTransaction().commit();
        } catch (EntityNotFound e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void updateOrdersBeforeAddBook(Integer bookId) {
        try {
            String hql = "UPDATE OrderEntity o SET o.statusOrder='NEW', o.dateComplection=:dateComplection WHERE o.book.id=:bookId";

            session.beginTransaction();

            session.createQuery(hql).setParameter("dateComplection", LocalDate.now().plusDays(7))
                        .setParameter("bookId", bookId)
                            .executeUpdate();

            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
        }
    }

    public void updateStatusOrder(int orderId, String statusOrder, String impossibleStatusOrder) throws EntityNotFound {
        try {
            session.beginTransaction();
            OrderEntity order = session.find(OrderEntity.class, orderId);
            if (order == null) {
                throw new EntityNotFound(super.messageOrderNotFound);
            }
            if (order.getStatusOrder().equals(impossibleStatusOrder)) {
                return;
            }
            order.setStatusOrder(statusOrder);
            order.setStatusOrder(statusOrder);
            session.update(order);
            session.getTransaction().commit();
        } catch (EntityNotFound e) {
            log.error(e.getMessage());
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<OrderEntity> getComplectedOrder(LocalDate startDate, LocalDate endDate) {

        String hqlAllOrder = "FROM BookEntity";
        String hqlComplectedOrder = "FROM OrderEntity r WHERE r.statusOrder='COMPLECTED' AND r.dateComplection BETWEEN :startDate AND :endDate";

        session.beginTransaction();

        List<BookEntity> books = session.createQuery(hqlAllOrder, BookEntity.class).getResultList();
        List<OrderEntity> complectedOrders = session.createQuery(hqlComplectedOrder, OrderEntity.class)
                .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                        .getResultList();

        for (OrderEntity order : complectedOrders) {
            for (BookEntity book : books) {
                if (order.getBook().getBookId().equals(book.getBookId())) {
                    order.setOrderPrice(book.getPrice());
                }
            }
        }
        session.getTransaction().commit();
        return complectedOrders;
    }

    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {

        String hql = "SELECT SUM(o.book.price) FROM OrderEntity o WHERE o.statusOrder = 'COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate";

        BigDecimal profit = session.createQuery(hql, BigDecimal.class)
                .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                        .getSingleResult();

        return profit;
    }

    public Long getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {

        String hql = "SELECT COUNT(*) FROM OrderEntity o WHERE o.statusOrder = 'COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate";

        Long countComplectedOrder = session.createQuery(hql, Long.class)
                .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                        .uniqueResult();

        return countComplectedOrder;
    }
}
