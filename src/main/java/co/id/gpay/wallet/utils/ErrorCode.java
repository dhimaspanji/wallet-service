package co.id.gpay.wallet.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DATA_NOT_FOUND(404, "DATA_NOT_FOUND"),
    MISSING_REQUIRED_HEADER(901, "MISSING_REQUIRED_HEADER"),
    INSUFFICIENT_BALANCE(101, "INSUFFICIENT_BALANCE"),
    DUPLICATE_TRANSACTION(102, "DUPLICATE_TRANSACTION"),
    CUSTOMER_NOT_FOUND(103, "CUSTOMER_NOT_FOUND"),
    INVALID_TRANSACTION_TYPE(104, "INVALID_TRANSACTION_TYPE"),;

    private final int code;
    private final String message;
}
