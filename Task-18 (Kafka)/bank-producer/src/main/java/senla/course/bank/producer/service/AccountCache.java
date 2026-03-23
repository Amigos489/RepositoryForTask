package senla.course.bank.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import senla.course.bank.producer.entity.Account;
import org.springframework.stereotype.Component;
import senla.course.bank.producer.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountCache {

    private final BigDecimal BALANCE = BigDecimal.valueOf(10000);

    private volatile boolean initialized = false;

    private static final Logger log = LoggerFactory.getLogger(AccountCache.class);

    private final AccountRepository accountRepository;

    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();

    public AccountCache(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void init() {
        accounts.clear();

        List<Account> accountFromDataBase = accountRepository.findAll();

        if (accountFromDataBase.isEmpty()) {

            log.info("First launch of producer, init accounts");

            for (int i = 0; i < 1000; i++) {
                Account account = new Account();
                account.setBalance(BALANCE);

                account = accountRepository.save(account);
                accounts.put(account.getId(), account);
            }
        } else {

            log.info("In database already exist accounts");

            for (Account account : accountFromDataBase) {
                accounts.put(account.getId(), account);
            }
        }

        initialized = true;
        log.info("Account cache initialized, size={}", accounts.size());
    }

    public boolean isInitialized() {
        return initialized;
    }

    public Map<Long, Account> getAccounts() {
        return accounts;
    }
}
