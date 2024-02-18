package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private Long id;
    private String street;
    @Column(name = "number")
    private String streetNumber;
    @Column(name = "zip_code")
    private String zipCode;
    private String city;
    @Column(name = "province_or_state")
    private String provinceOrState;
    private String country;
}
