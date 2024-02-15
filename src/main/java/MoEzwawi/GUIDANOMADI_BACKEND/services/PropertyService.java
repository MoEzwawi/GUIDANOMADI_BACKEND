package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Address;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Image;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyResponseDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.UpdateAddressDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.UpdatePropertyDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ImageService imageService;
    public Page<Property> getAllProperties(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.propertyRepository.findAll(pageable);
    }
    public Page<Property> getPropertiesByOwner(int page, int size, String orderBy, User owner){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return this.propertyRepository.findByUser(owner,pageable);
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
                body.provinceOrStateCode(), body.country());
        Property newProperty = this.propertyRepository.save(new Property(body.listingType(),propertyAddress,currentUser));
        return new NewPropertyResponseDTO(newProperty.getId());
    }
    public Property findById(Long id){
        return this.propertyRepository.findById(id).orElseThrow(()->new NotFoundException(id));
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
    public Property findByIdAndUpdateAddress(Long id,UpdateAddressDTO body){
        Property found = this.findById(id);
        this.addressService.findByIdAndUpdate(found.getId(), body);
        return found;
    }
    public void findByIdAndDelete(Long id){
        Property found = this.findById(id);
        this.propertyRepository.delete(found);
    }
    public Image uploadImage(Long id , MultipartFile file) throws IOException {
        Property found = this.findById(id);
        return this.imageService.save(found,file);
    }
    public Image uploadImage(Long id , String url) {
        Property found = this.findById(id);
        return this.imageService.save(found,url);
    }
    public Image uploadThumbnail(Long id, MultipartFile file){
    }
}