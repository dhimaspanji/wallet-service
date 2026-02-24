package co.id.gpay.wallet.model.response;

import co.id.gpay.wallet.model.base.data.BaseDataResponse;
import co.id.gpay.wallet.utils.TransactionStatus;
import io.soabase.recordbuilder.core.RecordBuilder;

import java.math.BigDecimal;

@RecordBuilder
public record TransactionResponse(
        String referenceNo,
        BigDecimal balance,
        TransactionStatus status
) implements BaseDataResponse {
}
