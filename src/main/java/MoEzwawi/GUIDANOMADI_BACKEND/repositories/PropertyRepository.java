package MoEzwawi.GUIDANOMADI_BACKEND.repositories;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
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
    @Query("SELECT p FROM Property p WHERE LOWER(p.address.city) = LOWER(:city)")
    Page<Property> findByAddress_City(String city, Pageable pageable);
}
