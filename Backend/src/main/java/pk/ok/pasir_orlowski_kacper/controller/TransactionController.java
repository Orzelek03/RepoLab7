package pk.ok.pasir_orlowski_kacper.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.ok.pasir_orlowski_kacper.model.Transaction;
import pk.ok.pasir_orlowski_kacper.dto.TransactionDTO;
import pk.ok.pasir_orlowski_kacper.service.TransactionService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() throws AccessDeniedException {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) throws AccessDeniedException {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDTO transactionDTO) throws AccessDeniedException {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(@PathVariable Long id) throws AccessDeniedException {
        transactionService.deleteTransaction(id); // Logika usuwania powinna być w serwisie
        return ResponseEntity.ok(Map.of("deleted", Boolean.TRUE));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transaction) throws AccessDeniedException {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }
}