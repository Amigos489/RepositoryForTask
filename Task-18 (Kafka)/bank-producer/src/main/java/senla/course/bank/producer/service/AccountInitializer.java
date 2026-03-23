package senla.course.bank.producer.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AccountInitializer {

    private final AccountCache accountCache;

    public AccountInitializer(AccountCache accountCache) {
        this.accountCache = accountCache;
    }

    @PostConstruct
    public void initAccountCache() {
        accountCache.init();
    }
}
