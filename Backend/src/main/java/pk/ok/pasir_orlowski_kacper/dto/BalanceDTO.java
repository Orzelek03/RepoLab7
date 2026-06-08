package pk.ok.pasir_orlowski_kacper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import pk.ok.pasir_orlowski_kacper.model.Transaction;
import pk.ok.pasir_orlowski_kacper.model.TransactionType;
import pk.ok.pasir_orlowski_kacper.model.User;

import java.util.List;

@Data
@AllArgsConstructor
public class BalanceDTO {
    private double totalIncome;
    private double totalExpense;
    private double balance;
    public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        List<Transaction> findByUser(User user);
    }

}