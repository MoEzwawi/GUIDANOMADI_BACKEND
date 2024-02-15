package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "property_images")
@Getter
@NoArgsConstructor
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
    public PropertyImage(Property property, String imageUrl) {
        this.property = property;
        this.imageUrl = imageUrl;
        this.isThumbnail = false;
    }
    public void setThumbnail() {
        this.isThumbnail = true;
    }
    public void setNotThumbnail(){
        this.isThumbnail = false;
    }
}
