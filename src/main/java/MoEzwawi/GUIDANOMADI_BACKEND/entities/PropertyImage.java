package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "property_images")
public class PropertyImage {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    @Column(name = "imageUrl")
    private String imageUrl;
    private boolean isThumbnail;
}
