package pk.ok.pasir_orlowski_kacper.dto;
import java.util.List;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupTransactionDTO {
    @NotNull(message = "Id grupy nie może być puste")
    private Long groupId;

    @NotNull(message = "Kwota nie może być pusta")
    @Positive(message = "Kwota musi być większa od zera")
    private Double amount;

    @NotBlank(message = "Typ transakcji nie może być pusty")
    @Pattern(regexp = "INCOME|EXPENSE", message = "Typ transakcji musi mieć wartość INCOME albo EXPENSE")
    private String type;

    @NotBlank(message = "Tytuł nie może być pusty")
    @Size(max = 100, message = "Tytuł nie może przekraczać 100 znaków")
    private String title;

    private List<Long> selectedUserIds;

    public List<Long> getSelectedUserIds() {
        return selectedUserIds;
    }

    public void setSelectedUserIds(List<Long> selectedUserIds) {
        this.selectedUserIds = selectedUserIds;
    }
}