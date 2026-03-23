package senla.course.bank.consumer.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import senla.course.bank.consumer.entity.Account;

@Repository
public class AccountRepository {

    @PersistenceContext
    private EntityManager em;

    public Account find(Long id) {
        return em.find(Account.class, id);
    }

    public Account update(Account acc) {
        return em.merge(acc);
    }
}
