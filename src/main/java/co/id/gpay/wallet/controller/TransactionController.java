package co.id.gpay.wallet.controller;

import co.id.gpay.wallet.header.Header;
import co.id.gpay.wallet.header.RequestContext;
import co.id.gpay.wallet.header.RequestHeader;
import co.id.gpay.wallet.model.request.TransactionRequest;
import co.id.gpay.wallet.model.request.TransferRequest;
import co.id.gpay.wallet.model.response.TransactionResponse;
import co.id.gpay.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @Header
    @PostMapping("/transactions")
    public TransactionResponse trx(@RequestBody TransactionRequest body) {
        RequestHeader header = RequestContext.get();
        return service.process(header, body);
    }

    @Header
    @PostMapping("/transfers")
    public void transfer(@RequestBody TransferRequest body) {
        RequestHeader header = RequestContext.get();
        service.transfer(header, body);
    }
}
