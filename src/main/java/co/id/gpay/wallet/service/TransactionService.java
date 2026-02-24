package co.id.gpay.wallet.service;

import co.id.gpay.wallet.exception.ServiceException;
import co.id.gpay.wallet.header.RequestHeader;
import co.id.gpay.wallet.model.entity.Account;
import co.id.gpay.wallet.model.entity.AccountMutation;
import co.id.gpay.wallet.model.entity.Transaction;
import co.id.gpay.wallet.model.repository.AccountMutationRepository;
import co.id.gpay.wallet.model.repository.AccountRepository;
import co.id.gpay.wallet.model.repository.TransactionRepository;
import co.id.gpay.wallet.model.request.TransactionRequest;
import co.id.gpay.wallet.model.request.TransferRequest;
import co.id.gpay.wallet.model.response.TransactionResponse;
import co.id.gpay.wallet.utils.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepo;
    private final TransactionRepository trxRepo;
    private final AccountMutationRepository mutationRepo;

    @Transactional
    public TransactionResponse process(RequestHeader header, TransactionRequest req) {
        var requestId = header.requestId();
        var channel = header.channel();

        trxRepo.findByRequestIdAndChannel(requestId, channel)
                .ifPresent(x -> {
                    throw new ServiceException(ErrorCode.DUPLICATE_TRANSACTION);
                });

        Account acc = accountRepo.findByIdForUpdate(req.accountId())
                .orElseThrow(() -> new ServiceException(ErrorCode.CUSTOMER_NOT_FOUND));

        BigDecimal before = acc.getBalance();

        BigDecimal after = calculate(before, req.amount(), req.type());

        if (after.compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        Transaction trx = trxRepo.save(
                Transaction.builder()
                        .referenceNo(req.referenceNo())
                        .requestId(requestId)
                        .channel(channel)
                        .accountId(acc.getId())
                        .type(req.type())
                        .amount(req.amount())
                        .status(TransactionStatus.SUCCESS)
                        .build()
        );

        createMutation(trx, acc, req.amount(), req.type(), before, after);

        acc.setBalance(after);

        accountRepo.save(acc);

        return new TransactionResponse(
                trx.getReferenceNo(),
                after,
                trx.getStatus()
        );
    }

    @Transactional
    public void transfer(RequestHeader header, TransferRequest req) {
        var requestId = header.requestId();
        var channel = header.channel();

        Account sender = accountRepo.findByIdForUpdate(req.fromAccountId())
                .orElseThrow(() -> new ServiceException(ErrorCode.DATA_NOT_FOUND));

        Account receiver = accountRepo.findByIdForUpdate(req.toAccountId())
                .orElseThrow(() -> new ServiceException(ErrorCode.DATA_NOT_FOUND));

        if (sender.getBalance().compareTo(req.amount()) < 0) {
            throw new ServiceException(ErrorCode.INSUFFICIENT_BALANCE);
        }

        BigDecimal senderBefore = sender.getBalance();
        BigDecimal receiverBefore = receiver.getBalance();

        sender.setBalance(senderBefore.subtract(req.amount()));

        receiver.setBalance(receiverBefore.add(req.amount()));

        accountRepo.save(sender);
        accountRepo.save(receiver);

        Transaction trx = trxRepo.save(
                Transaction.builder()
                    .referenceNo(req.referenceNo())
                    .requestId(requestId)
                    .channel(channel)
                    .type(TransactionType.TRANSFER)
                    .amount(req.amount())
                    .status(TransactionStatus.SUCCESS)
                    .build()
                );

        createMutation(trx, sender, req.amount(), TransactionType.TRANSFER, senderBefore, sender.getBalance());

        createMutation(trx, receiver, req.amount(), TransactionType.TOPUP, receiverBefore, receiver.getBalance());
    }

    private BigDecimal calculate(BigDecimal current, BigDecimal amount, TransactionType type) {
        return switch (type) {
            case TOPUP, REFUND, REVERSAL ->
                    current.add(amount);

            case PAYMENT, TRANSFER ->
                    current.subtract(amount);
        };
    }

    private void createMutation(Transaction trx, Account acc, BigDecimal amount, TransactionType type, BigDecimal before, BigDecimal after) {
        EntryType entryType =
                switch (type) {
                    case TOPUP, REFUND, REVERSAL ->
                            EntryType.CREDIT;

                    default -> EntryType.DEBIT;
                };

        mutationRepo.save(
                AccountMutation.builder()
                        .transactionId(trx.getId())
                        .accountId(acc.getId())
                        .entryType(entryType)
                        .amount(amount)
                        .balanceBefore(before)
                        .balanceAfter(after)
                        .build()
        );
    }
}
