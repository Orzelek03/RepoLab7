package pk.ok.pasir_orlowski_kacper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.ok.pasir_orlowski_kacper.model.Group;
import pk.ok.pasir_orlowski_kacper.model.User;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMemberships_User(User user);
}