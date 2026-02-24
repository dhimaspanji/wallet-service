package co.id.gpay.wallet.controller;

import co.id.gpay.wallet.model.entity.Account;
import co.id.gpay.wallet.model.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepo;

    @PostMapping("/accounts")
    public void createAccount() {
        accountRepo.save(Account.builder().balance(BigDecimal.ZERO).build());
    }

}
