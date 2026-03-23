package senla.course.bank.consumer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import senla.course.bank.common.dto.TransferDto;
import senla.course.bank.consumer.exception.ValidationException;
import senla.course.bank.consumer.service.TransferSaver;
import senla.course.bank.consumer.service.TransferService;

import java.util.List;

@Component
public class TransferListener {

    private final TransferSaver saver;

    private final TransferService service;

    private static final Logger log = LoggerFactory.getLogger(TransferListener.class);

    public TransferListener(TransferService service, TransferSaver saver) {
        this.service = service;
        this.saver = saver;
    }

    @KafkaListener(
            topics = "bank-topic",
            groupId = "bank-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(List<TransferDto> messages) {

        log.info("RECEIVED BATCH size={}", messages.size());

        for (TransferDto dto : messages) {

            try {
                service.process(dto);
                log.info("SUCCESS: {}", dto.getId());

            } catch (ValidationException e) {
                log.warn("VALIDATION ERROR: {}", dto.getId(), e);
                saver.saveFailed(dto);

            } catch (Exception e) {
                log.error("TRANSACTION ERROR: {}", dto.getId(), e);
                saver.saveFailed(dto);
            }
        }
    }
}
