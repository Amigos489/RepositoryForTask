package senla.course.bank.consumer.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.course.bank.common.dto.TransferDto;
import senla.course.bank.consumer.entity.Account;
import senla.course.bank.consumer.entity.Transfer;
import senla.course.bank.consumer.exception.ValidationException;


@Service
public class TransferService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void process(TransferDto dto) {

        Transfer existing = em.find(Transfer.class, dto.getId());
        if (existing != null) {
            return;
        }

        doTransfer(dto);
        saveTransfer(dto, "SUCCESS");
    }

    private void saveTransfer(TransferDto dto, String status) {

        Transfer t = new Transfer(dto, status);
        em.persist(t);
    }

    private void doTransfer(TransferDto dto) {

        Account from = em.find(Account.class, dto.getFromAccountId(), LockModeType.PESSIMISTIC_WRITE);
        Account to = em.find(Account.class, dto.getToAccountId(), LockModeType.PESSIMISTIC_WRITE);

        if (from == null || to == null) {
            throw new ValidationException("Account not found");
        }

        if (from.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new ValidationException("Not enough money");
        }

        from.setBalance(from.getBalance().subtract(dto.getAmount()));
        to.setBalance(to.getBalance().add(dto.getAmount()));
    }
}
