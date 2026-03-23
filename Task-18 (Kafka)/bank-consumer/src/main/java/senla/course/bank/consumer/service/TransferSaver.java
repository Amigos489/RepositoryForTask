package senla.course.bank.consumer.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import senla.course.bank.common.dto.TransferDto;
import senla.course.bank.consumer.entity.Transfer;

@Service
public class TransferSaver {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFailed(TransferDto dto) {

        Transfer t = new Transfer(dto, "FAILED");
        em.persist(t);
    }
}