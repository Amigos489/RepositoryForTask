package senla.course.bank.consumer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import senla.course.bank.common.dto.TransferDto;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    private UUID id;

    @Column(name = "from_account_id")
    private Long fromAccountId;
    @Column(name = "to_account_id")
    private Long toAccountId;
    private BigDecimal amount;
    private String status;

    public Transfer() {
    }

    public Transfer(TransferDto dto, String status) {
        this.id = dto.getId();
        this.fromAccountId = dto.getFromAccountId();
        this.toAccountId = dto.getToAccountId();
        this.amount = dto.getAmount();
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
