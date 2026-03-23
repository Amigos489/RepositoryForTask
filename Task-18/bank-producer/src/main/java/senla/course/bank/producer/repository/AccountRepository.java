package senla.course.bank.producer.repository;

import senla.course.bank.producer.entity.Account;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Account> findAll() {
        return em.createQuery("from Account", Account.class).getResultList();
    }

    public Account find(Long id) {
        return em.find(Account.class, id);
    }

    public Account save(Account acc) {
        em.persist(acc);
        return acc;
    }

    public Account update(Account acc) {
        return em.merge(acc);
    }
}
