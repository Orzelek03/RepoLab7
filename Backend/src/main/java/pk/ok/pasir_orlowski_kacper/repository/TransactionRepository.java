package pk.ok.pasir_orlowski_kacper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.ok.pasir_orlowski_kacper.model.Transaction;
import pk.ok.pasir_orlowski_kacper.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndTimestampAfter(User user, LocalDateTime startDate);
}
