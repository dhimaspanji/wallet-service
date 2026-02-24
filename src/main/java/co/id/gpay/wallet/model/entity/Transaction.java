package co.id.gpay.wallet.model.entity;

import co.id.gpay.wallet.model.base.BaseEntity;
import co.id.gpay.wallet.utils.ChannelType;
import co.id.gpay.wallet.utils.TransactionStatus;
import co.id.gpay.wallet.utils.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

@Entity
@Table(
        name = "transaction",
        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_reference_no",
                        columnNames = "referenceNo"
                ),

                @UniqueConstraint(
                        name = "uk_request_channel_partner",
                        columnNames = {
                                "requestId",
                                "channel"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String referenceNo;

    @Column(nullable = false)
    private String requestId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChannelType channel;

    private String accountId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
