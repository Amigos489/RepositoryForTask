package senla.course.bank.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import senla.course.bank.common.dto.TransferDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProducerService {

    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);
    private final KafkaTemplate<String, TransferDto> kafkaTemplate;
    private final AccountCache cache;
    private final Random random = new Random();

    public ProducerService(KafkaTemplate<String, TransferDto> kafkaTemplate,
                           AccountCache cache) {
        this.kafkaTemplate = kafkaTemplate;
        this.cache = cache;
    }

    @Scheduled(fixedDelay = 200, initialDelay = 5000)
    public void send() {

        if (!cache.isInitialized() || cache.getAccounts().isEmpty()) {
            log.warn("cache not ready");
            return;
        }


        List<Long> ids = new ArrayList<>(cache.getAccounts().keySet());

        int fromIndex = random.nextInt(ids.size());
        int toIndex = random.nextInt(ids.size() - 1);

        if (toIndex >= fromIndex) {
            toIndex++;
        }

        Long from = ids.get(fromIndex);
        Long to = ids.get(toIndex);

        TransferDto dto = new TransferDto();
        dto.setId(UUID.randomUUID());
        dto.setFromAccountId(from);
        dto.setToAccountId(to);
        dto.setAmount(BigDecimal.valueOf(random.nextInt(1000) + 1));

        log.info("SEND MESSAGE: id={}, from={}, to={}, amount={}",
                dto.getId(), from, to, dto.getAmount());

        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(
                    "bank-topic",
                    dto.getFromAccountId().toString(),
                    dto
            );
            return true;
        });
    }
}
