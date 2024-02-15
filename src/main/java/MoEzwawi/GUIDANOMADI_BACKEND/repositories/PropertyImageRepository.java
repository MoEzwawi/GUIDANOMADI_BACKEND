package MoEzwawi.GUIDANOMADI_BACKEND.repositories;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage,Long> {
}
