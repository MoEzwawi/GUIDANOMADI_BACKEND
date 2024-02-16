package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Favourite;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.FavouritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavouritesService {
    @Autowired
    private FavouritesRepository favouritesRepository;
    public Favourite findById(Long id){
        return this.favouritesRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public Optional<Favourite> findByUserAndProperty(User currentUser, Property property){
        return this.favouritesRepository.findByUserAndProperty(currentUser, property);
    }
    public boolean isItFavourite(User currentUser, Property property){
        return this.findByUserAndProperty(currentUser, property).isPresent();
    }
    public Favourite addToFavourites(User currentUser, Property property){
        return this.favouritesRepository.save(new Favourite(currentUser,property));
    }
    public Page<Favourite> getMyFavourites(int page, int size, String orderBy, User currentUser){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.favouritesRepository.findByUser(currentUser, pageable);
    }
    public void removeFromFavourites(Long id){
        Favourite found = this.findById(id);
        this.favouritesRepository.delete(found);
    }
    public void removeFromFavourites(User currentUser, Property property){
        if (this.findByUserAndProperty(currentUser, property).isPresent()){
            Favourite found = this.findByUserAndProperty(currentUser, property).get();
            this.favouritesRepository.delete(found);
        }
    }
}