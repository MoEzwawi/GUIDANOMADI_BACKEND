package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.*;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyResponseDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.UpdateAddressDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.UpdatePropertyDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private FavouritesService favouritesService;
    @Autowired
    private UsersService usersService;
    public Page<Property> getAllProperties(int page, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.propertyRepository.findAll(pageable);
    }
    public Page<Property> getPropertiesByOwner(int page, int size, String orderBy, User owner){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.propertyRepository.findByUser(owner,pageable);
    }
    public Page<Property> getPropertiesByOwner(int page, int size, String orderBy, UUID ownerId){
        User owner = this.usersService.findById(ownerId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.propertyRepository.findByUser(owner,pageable);
    }
    public Page<Property> getFavouritePropertiesByUser(int page, int size, String orderBy, User user){
        Page<Favourite> favPage = this.favouritesService.getMyFavourites(page, size, orderBy, user);
        List<Property> propertyList = favPage.stream().map(Favourite::getProperty).toList();
        return new PageImpl<>(propertyList, favPage.getPageable(), favPage.getTotalElements());
    }
    public Page<Property> getFavouritePropertiesByUser(int page, int size, String orderBy, UUID userId){
        User user = this.usersService.findById(userId);
        Page<Favourite> favPage = this.favouritesService.getMyFavourites(page, size, orderBy, user);
        List<Property> propertyList = favPage.stream().map(Favourite::getProperty).toList();
        return new PageImpl<>(propertyList, favPage.getPageable(), favPage.getTotalElements());
    }
    public Page<Property> getPropertiesByCountry(int page, int size, String orderBy, String country){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.propertyRepository.findByAddress_Country(country,pageable);
    }
    public Page<Property> getPropertiesByCity(int page, int size, String orderBy, String city){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.propertyRepository.findByAddress_City(city,pageable);
    }
    public NewPropertyResponseDTO saveNewProperty(NewPropertyDTO body, User currentUser){
        Address propertyAddress = this.addressService.save(body.street(),
                body.streetNumber(), body.zipCode(), body.city(),
                body.provinceOrState(), body.country());
        Property newProperty = this.propertyRepository.save(new Property(body.listingType(),propertyAddress,currentUser));
        return new NewPropertyResponseDTO(newProperty.getId());
    }
    public Property findById(Long id){
        return this.propertyRepository.findById(id).orElseThrow(()->new NotFoundException(id));
    }
    public Address findPropertyAddress(Long propertyId){
        Property found = this.findById(propertyId);
        return found.getAddress();
    }
    public Image getPropertyThumbnail(Long id){
        Property found = this.findById(id);
        return this.imageService.findThumbnail(found);
    }
    public List<Image> getPropertyImages(Long id){
        Property found = this.findById(id);
        return this.imageService.findByProperty(found);
    }
    public boolean canIEditTheCurrentProperty(User currentUser, Long id){
        Property found = this.findById(id);
        return Objects.equals(found.getListedBy().getEmail(), currentUser.getEmail());
    }
    public Property findByIdAndUpdate(Long id, UpdatePropertyDTO body){
        Property found = this.findById(id);
        if (body.title() != null) found.setTitle(body.title());
        if (body.description() != null) found.setDescription(body.description());
        if (body.whyIsPerfect() != null) found.setWhyIsPerfect(body.whyIsPerfect());
        if (body.numberOfRooms() != null) found.setNumberOfRooms(body.numberOfRooms());
        if (body.sizeSqMeters() != null) found.setSizeSqMeters(body.sizeSqMeters());
        if (body.floorNumber() != null) found.setFloorNumber(body.floorNumber());
        if (body.numberOfToilets() != null) found.setNumberOfToilets(body.numberOfToilets());
        if (body.price() != null) found.setPrice(body.price());
        if (body.availableFrom() != null) found.setAvailableFrom(body.availableFrom());
        return this.propertyRepository.save(found);
    }
    public Address findByIdAndUpdateAddress(Long id,UpdateAddressDTO body){
        Property found = this.findById(id);
        return this.addressService.findByIdAndUpdate(found.getAddress().getId(), body);
    }
    public void findByIdAndDelete(Long id){
        Property found = this.findById(id);
        this.propertyRepository.delete(found);
    }
    public boolean isThisPropertyAmongMyFavourites(User curentUser, Long id){
        Property found = this.findById(id);
        return this.favouritesService.isItFavourite(curentUser, found);
    }
    public void findPropertyByIdAndAddToFavourites(User currentUser, Long id){
        Property fav = this.findById(id);
        this.favouritesService.addToFavourites(currentUser,fav);
    }
    public void findPropertyByIdAndRemoveFromFavourites(User currentUser, Long id){
        Property fav = this.findById(id);
        this.favouritesService.removeFromFavourites(currentUser,fav);
    }
    public Image uploadImage(Long id , MultipartFile file) throws IOException {
        Property found = this.findById(id);
        return this.imageService.save(found,file);
    }
    public Image uploadImage(Long id , String url) {
        Property found = this.findById(id);
        return this.imageService.save(found,url);
    }
    public Image uploadThumbnail(Long id, MultipartFile file) throws IOException {
        Property found = this.findById(id);
        return this.imageService.uploadThumbnail(found,file);
    }
    public Image updateThumbnail(Long propertyId, Long imageId){
        Property found = this.findById(propertyId);
        return this.imageService.setImageAsNewThumbnail(found, imageId);
    }
}