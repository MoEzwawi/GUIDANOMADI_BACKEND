package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Address;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.UpdateAddressDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    public Address save(String street, String streetNumber, String zipCode,
                        String city, String provinceOrStateCode, String country){
        Address newAddress = new Address();
        newAddress.setStreet(street);
        newAddress.setStreetNumber(streetNumber);
        newAddress.setZipCode(zipCode);
        newAddress.setCity(city);
        newAddress.setProvinceOrState(provinceOrStateCode);
        newAddress.setCountry(country);
        return this.addressRepository.save(newAddress);
    }
    public Address findById(Long id){
        return this.addressRepository.findById(id).orElseThrow(()->new NotFoundException(id));
    }
    public Address findByIdAndUpdate(Long id, UpdateAddressDTO body){
        Address found = this.findById(id);
        if(body.street() != null) found.setStreet(body.street());
        if(body.streetNumber() != null) found.setStreetNumber(body.streetNumber());
        if(body.zipCode() != null) found.setZipCode(body.zipCode());
        if(body.city() != null) found.setCity(body.city());
        if(body.provinceOrState() != null) found.setProvinceOrState(body.provinceOrState());
        if (body.country() != null) found.setCountry(body.country());
        return this.addressRepository.save(found);
    }
    public void findByIdAndDelete(Long id){
        Address found = this.findById(id);
        this.addressRepository.delete(found);
    }
}
