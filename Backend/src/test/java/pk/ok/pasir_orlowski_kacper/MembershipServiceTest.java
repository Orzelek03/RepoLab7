package pk.ok.pasir_orlowski_kacper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pk.ok.pasir_orlowski_kacper.dto.MembershipDTO;
import pk.ok.pasir_orlowski_kacper.model.Group;
import pk.ok.pasir_orlowski_kacper.model.Membership;
import pk.ok.pasir_orlowski_kacper.model.User;
import pk.ok.pasir_orlowski_kacper.repository.GroupRepository;
import pk.ok.pasir_orlowski_kacper.repository.MembershipRepository;
import pk.ok.pasir_orlowski_kacper.service.CurrentUserService;
import pk.ok.pasir_orlowski_kacper.service.MembershipService;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {
    @Mock
    private MembershipRepository membershipRepository;
    @Mock private GroupRepository groupRepository;
    @Mock private CurrentUserService currentUserService;
    @InjectMocks
    private MembershipService membershipService;

    @Test
    void shouldNotAllowRemovingGroupOwner() {
        //
        User owner = new User(); owner.setId(1L);
        Group group = new Group(); group.setOwner(owner);
        Membership membership = new Membership(); membership.setUser(owner); membership.setGroup(group);

        when(membershipRepository.findById(anyLong())).thenReturn(Optional.of(membership));
        when(currentUserService.getCurrentUser()).thenReturn(owner);

        assertThrows(IllegalStateException.class, () -> membershipService.removeMember(1L));
    }

    @Test
    void onlyGroupOwnerCanAddMembers() {
        //
        User owner = new User(); owner.setId(1L);
        User nonOwner = new User(); nonOwner.setId(2L);
        Group group = new Group(); group.setOwner(owner);

        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(group));
        when(currentUserService.getCurrentUser()).thenReturn(nonOwner);

        MembershipDTO dto = new MembershipDTO(); dto.setGroupId(1L);
        assertThrows(AccessDeniedException.class, () -> membershipService.addMember(dto));
    }
}