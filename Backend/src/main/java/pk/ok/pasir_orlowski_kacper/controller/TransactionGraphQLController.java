package pk.ok.pasir_orlowski_kacper.controller;

import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import pk.ok.pasir_orlowski_kacper.dto.BalanceDTO;
import pk.ok.pasir_orlowski_kacper.dto.TransactionDTO;
import pk.ok.pasir_orlowski_kacper.model.Transaction;
import pk.ok.pasir_orlowski_kacper.model.User;
import pk.ok.pasir_orlowski_kacper.service.TransactionService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
public class TransactionGraphQLController {

    private final TransactionService transactionService;

    public TransactionGraphQLController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @QueryMapping
    public List<Transaction> transactions() throws AccessDeniedException {
        return transactionService.getAllTransactions();
    }
    @MutationMapping
            (name = "addTransaction") // Możesz dodać (name = ...), żeby mieć pewność
    public Transaction addTransaction(@Argument TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }


    @MutationMapping
    public Transaction updateTransaction(
            @Argument Long id,
            @Argument TransactionDTO transactionDTO // Usuń (name = "...")
    ) throws AccessDeniedException {
        // Dodaj ten test, żeby upewnić się w konsoli, że dane płyną:
        System.out.println("DTO: " + transactionDTO);

        return transactionService.updateTransaction(id, transactionDTO);
    }

    @MutationMapping
    public Boolean deleteTransaction(@Argument Long id) throws AccessDeniedException {
        return transactionService.deleteTransaction(id);
    }
    @QueryMapping
    public BalanceDTO userBalance(@Argument Double days) throws AccessDeniedException {
        User user = transactionService.getCurrentUser();
        return transactionService.getUserBalance(user, days);
    }


}