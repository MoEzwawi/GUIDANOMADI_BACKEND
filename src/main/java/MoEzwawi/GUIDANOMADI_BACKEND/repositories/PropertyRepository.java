package MoEzwawi.GUIDANOMADI_BACKEND.repositories;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {
}
