package co.id.gpay.wallet.model.request;

import co.id.gpay.wallet.model.base.data.BaseDataRequest;
import co.id.gpay.wallet.utils.TransactionType;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@RecordBuilder
public record TransactionRequest(
        @NotBlank
        String referenceNo,
        @NotBlank
        String accountId,
        @NotNull
        @Positive
        BigDecimal amount,
        @NotNull
        TransactionType type
) implements BaseDataRequest {
}
