package pk.ok.pasir_orlowski_kacper.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "debts")
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String title;
    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private User debtor;

    @ManyToOne
    @JoinColumn(name = "creditor_id")
    private User creditor;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public String getTitle() {
        return title != null ? title : "Brak opisu";
    }
    private boolean paidByDebtor = false;
    private boolean confirmedByCreditor = false;

    public boolean isPaidByDebtor() {
        return paidByDebtor;
    }

    public void setPaidByDebtor(boolean paidByDebtor) {
        this.paidByDebtor = paidByDebtor;
    }

    public boolean isConfirmedByCreditor() {
        return confirmedByCreditor;
    }

    public void setConfirmedByCreditor(boolean confirmedByCreditor) {
        this.confirmedByCreditor = confirmedByCreditor;
    }
}