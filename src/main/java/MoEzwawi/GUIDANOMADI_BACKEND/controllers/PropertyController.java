package MoEzwawi.GUIDANOMADI_BACKEND.controllers;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Address;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Image;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotYourPropertyException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.properties.*;
import MoEzwawi.GUIDANOMADI_BACKEND.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            @RequestParam(defaultValue = "createdAt") String orderBy
    ){
        return this.propertyService.getAllProperties(page, size, orderBy);
    }
    @GetMapping("/byCountry")
    public Page<Property> getPropertiesByCountry(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam String country
    ){
        return this.propertyService.getPropertiesByCountry(page, size, orderBy, country);
    }
    @GetMapping("/byCity")
    public Page<Property> getPropertiesByCity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
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
    @PostMapping("/{id}/thumbnail")
    public Image uploadThumbnail(
            @PathVariable Long id,
            @RequestParam("thumbnail") MultipartFile file,
            @AuthenticationPrincipal User currentUser
    ) throws IOException {
        if (!this.propertyService.canIEditTheCurrentProperty(currentUser, id)){
            throw new NotYourPropertyException();
        }
        return this.propertyService.uploadThumbnail(id, file);
    }
    @PutMapping("/{id}/thumbnail/{imgId}")
    public Image updateThumbnail(
            @PathVariable Long id,
            @PathVariable Long imgId,
            @AuthenticationPrincipal User currentUser
    ){
        if (!this.propertyService.canIEditTheCurrentProperty(currentUser, id)){
            throw new NotYourPropertyException();
        }
        return this.propertyService.updateThumbnail(id, imgId);
    }
    @GetMapping("/{id}/images")
    public List<Image> getPropertyImages(@PathVariable Long id){
        return this.propertyService.getPropertyImages(id);
    }
    @GetMapping("/{id}/isItMine")
    public boolean isTheCurrentPropertyMine(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id
    ){
        return this.propertyService.canIEditTheCurrentProperty(currentUser, id);
    }
    @PutMapping("/{id}")
    public Property updatePropertyDetails(
            @PathVariable Long id,
            @RequestBody UpdatePropertyDTO body,
            @AuthenticationPrincipal User currentUser
    ){
        if (!this.propertyService.canIEditTheCurrentProperty(currentUser, id)){
            throw new NotYourPropertyException();
        }
        return this.propertyService.findByIdAndUpdate(id, body);
    }
    @GetMapping("/{id}/address")
    public Address getPropertyAddress(@PathVariable Long id){
        return this.propertyService.findPropertyAddress(id);
    }
    @PutMapping("/{id}/address")
    public Address updatePropertyAddress(
            @PathVariable Long id,
            @RequestBody UpdateAddressDTO body,
            @AuthenticationPrincipal User currentUser
    ){
        if (!this.propertyService.canIEditTheCurrentProperty(currentUser, id)){
            throw new NotYourPropertyException();
        }
        return this.propertyService.findByIdAndUpdateAddress(id, body);
    }
    @DeleteMapping("/{id}")
    public void deletePropertyListing(@PathVariable Long id, @AuthenticationPrincipal User currentUser){
        if (!this.propertyService.canIEditTheCurrentProperty(currentUser, id)){
            throw new NotYourPropertyException();
        }
        this.propertyService.findByIdAndDelete(id);
    }
    @GetMapping("/{id}/fav")
    public boolean isPropertyAmongFavourites(@AuthenticationPrincipal User currentUser ,@PathVariable Long id){
        return this.propertyService.isThisPropertyAmongMyFavourites(currentUser, id);
    }
    @PutMapping("/{id}/fav")
    public void changeFavouriteStatus(@AuthenticationPrincipal User currentUser ,@PathVariable Long id){
        if (this.propertyService.isThisPropertyAmongMyFavourites(currentUser, id)){
            this.propertyService.findPropertyByIdAndRemoveFromFavourites(currentUser, id);
        } else {
            this.propertyService.findPropertyByIdAndAddToFavourites(currentUser, id);
        }
    }
    @PostMapping("/{id}/uploadImgFile")
    public Image uploadImageFile(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file,
            @AuthenticationPrincipal User currentUser
    ) throws IOException {
        if (!this.propertyService.canIEditTheCurrentProperty(currentUser, id)){
            throw new NotYourPropertyException();
        }
        return this.propertyService.uploadImage(id, file);
    }
    @PostMapping("/{id}/uploadImgUrl")
    public Image uploadImageUrl(
            @PathVariable Long id,
            @RequestBody ImageUrlDTO body,
            @AuthenticationPrincipal User currentUser
    ){
        if (!this.propertyService.canIEditTheCurrentProperty(currentUser, id)){
            throw new NotYourPropertyException();
        }
        return this.propertyService.uploadImage(id, body.imgUrl());
    }
}
