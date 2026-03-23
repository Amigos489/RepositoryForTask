package senla.course.bank.consumer.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import senla.course.bank.consumer.entity.Transfer;

@Repository
public class TransferRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Transfer t) {
        em.persist(t);
    }
}
