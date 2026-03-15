package senla.course.dao;

import org.hibernate.SessionFactory;
import senla.course.entitys.BookEntity;
import senla.course.entitys.OrderEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderDaoImpl extends HibernateAbstractDao<OrderEntity, Integer> implements IOrderDao {

    public OrderDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, OrderEntity.class);
    }

    public OrderEntity findOrderById(Integer id) {
        return sessionFactory.getCurrentSession().find(OrderEntity.class, id);
    }

    @Override
    public List<OrderEntity> findAll(String criteria) {

        String hqlFindAllOrder;

        switch (criteria) {
            case "datecomplected": {
                hqlFindAllOrder = "SELECT o FROM OrderEntity o LEFT JOIN FETCH o.book ORDER BY o.dateComplection";
                break;
            }
            case "price": {
                hqlFindAllOrder = "SELECT o FROM OrderEntity o LEFT JOIN FETCH o.book ORDER BY o.book.price";
                break;
            }
            case "status": {
                hqlFindAllOrder = "SELECT o FROM OrderEntity o LEFT JOIN FETCH o.book ORDER BY o.statusOrder";
                break;
            }
            default: {
                hqlFindAllOrder = "SELECT o FROM OrderEntity o LEFT JOIN FETCH o.book";
            }
        }
        List<OrderEntity> orders = sessionFactory.getCurrentSession().createQuery(hqlFindAllOrder, OrderEntity.class).getResultList();

        for (OrderEntity order : orders) {
            order.setOrderPrice(order.getBook().getPrice());
        }

        return orders;
    }

    @Override
    public void createOrder(OrderEntity order) {
        BookEntity book = sessionFactory.getCurrentSession().find(BookEntity.class, order.getBookId());
        order.setBook(book);
        order.setOrderPrice(book.getPrice());
        sessionFactory.getCurrentSession().persist(order);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void updateOrdersBeforeAddBook(Integer bookId) {
        String hql = "UPDATE OrderEntity o SET o.statusOrder='NEW', o.dateComplection=:dateComplection WHERE o.book.id=:bookId AND o.statusOrder='WAITING'";
        sessionFactory.getCurrentSession().createQuery(hql).setParameter("dateComplection", LocalDate.now().plusDays(7))
                        .setParameter("bookId", bookId)
                            .executeUpdate();
    }

    @Override
    public void updateStatusOrder(int orderId, String statusOrder, String impossibleStatusOrder) {
        OrderEntity order = sessionFactory.getCurrentSession().find(OrderEntity.class, orderId);
        if (order.getStatusOrder().equals(impossibleStatusOrder)) {
            return;
        }
        order.setStatusOrder(statusOrder);
        sessionFactory.getCurrentSession().update(order);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public List<OrderEntity> getComplectedOrder(LocalDate startDate, LocalDate endDate, String criteria) {

        String hqlGetComplectedOrder;

        switch (criteria) {
            case "datecomplected": {
                hqlGetComplectedOrder = "SELECT o FROM OrderEntity o JOIN FETCH o.book WHERE o.statusOrder='COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate ORDER BY o.dateComplection";
                break;
            }
            case "price": {
                hqlGetComplectedOrder = "SELECT o FROM OrderEntity o JOIN FETCH o.book WHERE o.statusOrder='COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate ORDER BY o.dateComplection";
                break;
            }
            default: {
                hqlGetComplectedOrder = "SELECT o FROM OrderEntity o JOIN FETCH o.book WHERE o.statusOrder='COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate";
            }
        }

        List<OrderEntity> complectedOrders = sessionFactory.getCurrentSession().createQuery(hqlGetComplectedOrder, OrderEntity.class)
                .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                        .getResultList();

        for (OrderEntity order : complectedOrders) {

            order.setOrderPrice(order.getBook().getPrice());
        }
        return complectedOrders;
    }

    @Override
    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {

        String hqlGetProfit = "SELECT SUM(o.book.price) FROM OrderEntity o WHERE o.statusOrder = 'COMPLECTED' AND o.dateComplection BETWEEN :startDate AND :endDate";

        BigDecimal profit = null;
        profit = sessionFactory.getCurrentSession().createQuery(hqlGetProfit, BigDecimal.class)
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
        countComplectedOrder = sessionFactory.getCurrentSession().createQuery(hqlGetCountComplectedOrder, Long.class)
                .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                        .getSingleResult();
        if (countComplectedOrder == null) {
            countComplectedOrder = 0L;
        }

        return countComplectedOrder;
    }
}
