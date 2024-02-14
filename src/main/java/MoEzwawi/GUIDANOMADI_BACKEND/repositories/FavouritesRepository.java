package MoEzwawi.GUIDANOMADI_BACKEND.repositories;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouritesRepository extends JpaRepository<Favourite,Long> {
}
