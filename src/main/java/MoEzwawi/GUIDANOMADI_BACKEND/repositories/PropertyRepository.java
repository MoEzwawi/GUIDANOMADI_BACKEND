package MoEzwawi.GUIDANOMADI_BACKEND.repositories;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.enums.ListingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {
    @Query("SELECT p FROM Property p WHERE p.listedBy = :user")
    Page<Property> findByUser(User user, Pageable pageable);
    @Query("SELECT p FROM Property p WHERE LOWER(p.address.country) = LOWER(:country)")
    Page<Property> findByAddress_Country(String country, Pageable pageable);
    @Query("SELECT p FROM Property p WHERE LOWER(p.address.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    Page<Property> findByAddress_City(String city, Pageable pageable);
    @Query("SELECT p FROM Property p WHERE p.listingType = :listingType")
    Page<Property> findByListingType(ListingType listingType, Pageable pageable);
    @Query("SELECT p FROM Property p WHERE LOWER(p.address.country) = LOWER(:country) AND LOWER(p.address.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    Page<Property> findByCountryAndCity(String country, String city, Pageable pageable);
    @Query("SELECT p FROM Property p WHERE LOWER(p.address.country) = LOWER(:country) AND p.listingType = :listingType")
    Page<Property> findByCountryAndListingType(String country, ListingType listingType, Pageable pageable);
    @Query("SELECT p FROM Property p WHERE LOWER(p.address.city) LIKE LOWER(CONCAT('%', :city, '%')) AND p.listingType = :listingType")
    Page<Property> findByCityAndListingType(String city, ListingType listingType, Pageable pageable);
    @Query("SELECT p FROM Property p WHERE LOWER(p.address.country) = LOWER(:country) AND LOWER(p.address.city) LIKE LOWER(CONCAT('%', :city, '%')) AND p.listingType = :listingType")
    Page<Property> findByCountryAndCityAndType(String country, String city, ListingType listingType, Pageable pageable);
}
