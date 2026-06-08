package pk.ok.pasir_orlowski_kacper.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Transaction entity with validation constraints.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @NotNull(message = "Kwota nie może być pusta")
    @DecimalMin(value = "0.01", message = "Kwota musi być większa od 0")
    private Double amount;

    @NotBlank(message = "Typ nie może być pusty")
    private String type; // Przesyłamy jako String, Service zamieni to na Enum

    @Size(max = 70, message = "Tagi muszą mieć najwyżej 70 znaków")
    private String tags;
    private String notes;
}