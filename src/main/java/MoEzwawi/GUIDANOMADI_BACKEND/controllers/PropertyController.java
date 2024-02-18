package MoEzwawi.GUIDANOMADI_BACKEND.controllers;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Address;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Image;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.NewPropertyResponseDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.UpdateAddressDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.UpdatePropertyDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;
    @GetMapping
    public Page<Property> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cretedAt") String orderBy
    ){
        return this.propertyService.getAllProperties(page, size, orderBy);
    }
    @GetMapping
    public Page<Property> getPropertiesByCountry(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cretedAt") String orderBy,
            @RequestParam String country
    ){
        return this.propertyService.getPropertiesByCountry(page, size, orderBy, country);
    }
    @GetMapping
    public Page<Property> getPropertiesByCity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cretedAt") String orderBy,
            @RequestParam String city
    ){
        return this.propertyService.getPropertiesByCity(page, size, orderBy, city);
    }
    @PostMapping
    public NewPropertyResponseDTO addPropertyListing(
            @RequestBody NewPropertyDTO body,
            @AuthenticationPrincipal User currentUser
    ){
        return this.propertyService.saveNewProperty(body, currentUser);
    }
    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Long id){
        return this.propertyService.findById(id);
    }
    @GetMapping("/{id}/thumbnail")
    public Image getPropertyThumbnail(@PathVariable Long id){
        return this.propertyService.getPropertyThumbnail(id);
    }
    @GetMapping("/{id}/images")
    public List<Image> getPropertyImages(@PathVariable Long id){
        return this.propertyService.getPropertyImages(id);
    }
    @PutMapping("/{id}")
    public Property updatePropertyDetails(
            @PathVariable Long id,
            @RequestBody UpdatePropertyDTO body
    ){
        return this.propertyService.findByIdAndUpdate(id, body);
    }
    @GetMapping("/{id}/address")
    public Address getPropertyAddress(@PathVariable Long id){
        return this.propertyService.findPropertyAddress(id);
    }
    @PutMapping("/{id}/address")
    public Address updatePropertyAddress(
            @PathVariable Long id,
            @RequestBody UpdateAddressDTO body
    ){
        return this.propertyService.findByIdAndUpdateAddress(id, body);
    }
    @DeleteMapping("/{id}")
    public void deletePropertyListing(@PathVariable Long id){
        this.propertyService.findByIdAndDelete(id);
    }
    @GetMapping("/{id}/favs")
    public boolean isPropertyAmongFavourites(@AuthenticationPrincipal User currentUser ,@PathVariable Long id){
        return this.propertyService.isThisPropertyAmongMyFavourites(currentUser, id);
    }
    @PutMapping("/{id}/favs")
    public void changeFavouritePreference(@AuthenticationPrincipal User currentUser ,@PathVariable Long id){
        if (this.propertyService.isThisPropertyAmongMyFavourites(currentUser, id)){
            this.propertyService.findPropertyByIdAndRemoveFromFavourites(currentUser, id);
        } else {
            this.propertyService.findPropertyByIdAndAddToFavourites(currentUser, id);
        }
    }
}
