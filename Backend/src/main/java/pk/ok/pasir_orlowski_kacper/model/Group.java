package pk.ok.pasir_orlowski_kacper.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"groups\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner; // Właściciel grupy [cite: 160]

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Membership> memberships;

    @Transient // Pole niewidoczne dla bazy, ale potrzebne dla GraphQL [cite: 167, 168]
    public Long getOwnerId() {
        return owner != null ? owner.getId() : null;
    }
}