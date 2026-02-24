package co.id.gpay.wallet.model.entity;

import co.id.gpay.wallet.utils.EntryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "account_mutation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountMutation {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    private String transactionId;
    private String accountId;

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    private BigDecimal amount;

    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE", updatable = false)
    private ZonedDateTime createdTime;
}
