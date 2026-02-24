package co.id.gpay.wallet.model.repository;

import co.id.gpay.wallet.model.entity.Transaction;
import co.id.gpay.wallet.utils.ChannelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Optional<Transaction> findByRequestIdAndChannel(
            String requestId,
            ChannelType channel
    );
}
