package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.enums.ListingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    @Setter(AccessLevel.NONE)
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();
    private String title;
    @Column(length = 500)
    private String description;
    @Column(name = "why_is_perfect",length = 500)
    private String whyIsPerfect;
    @Column(name = "room_count")
    private Integer numberOfRooms;
    @Column(name = "size_sq_meters")
    private Integer sizeSqMeters;
    @Column(name = "floor")
    private Integer floorNumber;
    @Column(name = "toilet_count")
    private Integer numberOfToilets;
    @Column(name = "listing_type")
    @Enumerated(EnumType.STRING)
    private ListingType listingType;
    private Double price;
    @Column(name = "available_from")
    private LocalDate availableFrom;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "listed_by_id")
    private User listedBy;
    public Property(ListingType listingType, Address address, User listedBy) {
        this.listingType = listingType;
        this.address = address;
        this.listedBy = listedBy;
    }
}
