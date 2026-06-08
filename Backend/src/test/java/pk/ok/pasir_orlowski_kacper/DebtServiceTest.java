package pk.ok.pasir_orlowski_kacper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pk.ok.pasir_orlowski_kacper.dto.DebtDTO;
import pk.ok.pasir_orlowski_kacper.repository.DebtRepository;
import pk.ok.pasir_orlowski_kacper.service.DebtService;
import pk.ok.pasir_orlowski_kacper.service.MembershipService;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class DebtServiceTest {
    @Mock
    private DebtRepository debtRepository;
    @Mock private MembershipService membershipService;
    @InjectMocks
    private DebtService debtService;

    @Test
    void shouldRejectDebtToSelf() {
        //
        DebtDTO dto = new DebtDTO();
        dto.setDebtorId(1L);
        dto.setCreditorId(1L); // Ten sam użytkownik

        assertThrows(IllegalStateException.class, () -> debtService.createDebt(dto));
    }

    @Test
    void shouldRejectDebtFromNonGroupMember() {
        //
        doThrow(new AccessDeniedException("..")).when(membershipService).assertUserIsGroupMember(any(), any());

        DebtDTO dto = new DebtDTO();
        dto.setGroupId(1L); dto.setDebtorId(5L); dto.setCreditorId(1L);

        assertThrows(AccessDeniedException.class, () -> debtService.createDebt(dto));
    }
}