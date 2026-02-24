package co.id.gpay.wallet.model.repository;

import co.id.gpay.wallet.model.entity.AccountMutation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountMutationRepository extends JpaRepository<AccountMutation, String> {
}
