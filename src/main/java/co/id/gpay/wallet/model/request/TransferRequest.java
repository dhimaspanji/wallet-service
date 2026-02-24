package co.id.gpay.wallet.model.request;

import co.id.gpay.wallet.model.base.data.BaseDataRequest;
import io.soabase.recordbuilder.core.RecordBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@RecordBuilder
public record TransferRequest(
        @NotBlank
        String referenceNo,
        @NotBlank
        String fromAccountId,
        @NotBlank
        String toAccountId,
        @NotNull
        @Positive
        BigDecimal amount
) implements BaseDataRequest {
}
