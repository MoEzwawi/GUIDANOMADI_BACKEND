package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Address;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyResponseDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private AddressService addressService;
    public NewPropertyResponseDTO saveNewProperty(NewPropertyDTO body, User currentUser){
        Address propertyAddress = this.addressService.save(body.street(),
                body.streetNumber(), body.zipCode(), body.city(),
                body.provinceOrStateCode(), body.country());
        Property newProperty = this.propertyRepository.save(new Property(body.listingType(),propertyAddress,currentUser));
        return new NewPropertyResponseDTO(newProperty.getId());
    }

}
