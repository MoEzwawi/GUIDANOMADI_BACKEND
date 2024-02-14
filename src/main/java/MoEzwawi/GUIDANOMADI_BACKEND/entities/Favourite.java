package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Favourites")
@Getter
@NoArgsConstructor
public class Favourite {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    public Favourite(User user, Property property) {
        this.user = user;
        this.property = property;
    }
}
