package dao;

import entitys.BookEntity;
import entitys.OrderEntity;
import exception.EntityListEmpty;
import exception.EntityNotFound;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderDaoImpl extends HibernateAbstractDao<OrderEntity, Integer> implements IOrderDao {

    private final Session session;

    public OrderDaoImpl(Session session) {
        this.session = session;
    }

    public OrderEntity findOrderById(Integer id) throws EntityNotFound {
        if (id == null || id <= 0) {
            throw new  IllegalArgumentException(super.messageIncorrectId);
        }
        OrderEntity orderEntity = session.find(OrderEntity.class, id);
        if (orderEntity == null) {
            throw new EntityNotFound(super.messageOrderNotFound);
        }
        orderEntity.setOrderPrice(orderEntity.getBook().getPrice());
        return orderEntity;
    }

    @Override
    public List<OrderEntity> findAll() {
        String hqlFindAllOrder = "SELECT o FROM OrderEntity o LEFT JOIN FETCH o.book";
        List<OrderEntity> orders = session.createQuery(hqlFindAllOrder, OrderEntity.class).getResultList();

        for (OrderEntity order : orders) {
            order.setOrderPrice(order.getBook().getPrice());
        }

        return orders;
    }

    @Override
    public void createOrder(OrderEntity order) throws EntityNotFound {
        BookEntity book = session.find(BookEntity.class, order.getBookId());
        if (book == null) {
            throw new EntityNotFound(super.messageBookNotFound);
        }
        order.setBook(book);
        order.setOrderPrice(book.getPrice());
        session.persist(order);
        session.flush();
    }

    @Override
    public void updateOrdersBeforeAddBook(Integer bookId) {
        String hql = "UPDATE OrderEntity o SET o.statusOrder='NEW', o.dateComplection=:dateComplection WHERE o.book.id=:bookId";
        session.createQuery(hql).setParameter("dateComplection", LocalDate.now().plusDays(7))
                        .setParameter("bookId", bookId)
                            .executeUpdate();
    }

    @Override
    public void updateStatusOrder(int orderId, String statusOrder, String impossibleStatusOrder) throws EntityNotFound {
        OrderEntity order = session.find(OrderEntity.class, orderId);
        if (order == null) {
            throw new EntityNotFound(super.messageOrderNotFound);
        }
        if (order.getStatusOrder().equals(impossibleStatusOrder)) {
            return;
        }
        order.setStatusOrder(statusOrder);
        session.update(order);
        session.flush();
    }

    @Override
    public List<OrderEntity> getComplectedOrder(LocalDate startDate, LocalDate endDate) throws EntityListEmpty {

        String hqlGetComplectedOrder = "SELECT o FROM OrderEntity o JOIN FETCH o.book WHERE o.statusOrder='COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate";

        List<OrderEntity> complectedOrders = session.createQuery(hqlGetComplectedOrder, OrderEntity.class)
                .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                        .getResultList();

        if (complectedOrders.isEmpty()) {
            throw new EntityListEmpty(super.messageEntityListEmpty);
        }

        for (OrderEntity order : complectedOrders) {

            order.setOrderPrice(order.getBook().getPrice());
        }
        return complectedOrders;
    }

    @Override
    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {

        String hqlGetProfit = "SELECT SUM(o.book.price) FROM OrderEntity o WHERE o.statusOrder = 'COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate";

        BigDecimal profit = null;
        profit = session.createQuery(hqlGetProfit, BigDecimal.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
        if (profit == null) {
            profit = BigDecimal.ZERO;
        }

        return profit;
    }

    @Override
    public Long getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {

        String hqlGetCountComplectedOrder = "SELECT COUNT(*) FROM OrderEntity o WHERE o.statusOrder = 'COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate";

        Long countComplectedOrder = null;
        countComplectedOrder = session.createQuery(hqlGetCountComplectedOrder, Long.class)
                .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                        .getSingleResult();
        if (countComplectedOrder == null) {
            countComplectedOrder = 0L;
        }

        return countComplectedOrder;
    }
}
