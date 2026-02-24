package co.id.gpay.wallet.model.repository;

import co.id.gpay.wallet.model.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select a from Account a where a.id = :id
    """)
    Optional<Account> findByIdForUpdate(String id);
}
