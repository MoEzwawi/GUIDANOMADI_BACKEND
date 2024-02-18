package MoEzwawi.GUIDANOMADI_BACKEND.controllers;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Favourite;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.UpdateEmailDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.UpdateNameAndSurnameDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.UpdatePasswordDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.services.AuthService;
import MoEzwawi.GUIDANOMADI_BACKEND.services.FavouritesService;
import MoEzwawi.GUIDANOMADI_BACKEND.services.PropertyService;
import MoEzwawi.GUIDANOMADI_BACKEND.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private AuthService authService;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "lastName") String orderBy){
        return this.usersService.getAllUsers(page, size, orderBy);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUserById(@PathVariable UUID id){
        return this.usersService.findById(id);
    }
    @PutMapping("/{id}/promote")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User promote(@PathVariable UUID id){
        return this.usersService.findByIdAndPromote(id);
    }
    @PutMapping("/{id}/downgrade")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User downgrade(@PathVariable UUID id){
        return this.usersService.findByIdAndDowngrade(id);
    }
    @GetMapping("/{id}/properties")
    public Page<Property> getPropertiesByOwnerId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @PathVariable UUID id
    ){
        return this.propertyService.getPropertiesByOwner(page, size, orderBy, id);
    }
    @GetMapping("/{id}/favourites")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Property> getUsersFavs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @PathVariable UUID id
    ){
        return this.propertyService.getFavouritePropertiesByUser(page, size, orderBy, id);
    }
    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal User currentUser){
        return currentUser;
    }
    @GetMapping("/me/myProperties")
    public Page<Property> getMyProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @AuthenticationPrincipal User currentUser
    ){
        return this.propertyService.getPropertiesByOwner(page,size,orderBy,currentUser);
    }
    @GetMapping("/me/myFavourites")
    public Page<Property> getMyFavourites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy,
            @AuthenticationPrincipal User currentUser
    ){
        return this.propertyService.getFavouritePropertiesByUser(page, size, orderBy, currentUser);
    }
    @PutMapping("/me/updateNameAndSurname")
    public User updateNameAndSurname(@AuthenticationPrincipal User currentUser, @RequestBody UpdateNameAndSurnameDTO body){
        return this.usersService.updateNameAndSurname(currentUser,body);
    }
    @PutMapping("/me/changeEmail")
    public User changeEmail(@AuthenticationPrincipal User currentUser, @RequestBody UpdateEmailDTO body){
        return this.usersService.changeEmail(currentUser, body);
    }
    @PutMapping("/me/changePassword")
    public void changePassword(@AuthenticationPrincipal User currentUser, @RequestBody UpdatePasswordDTO body){
        this.authService.changePassword(currentUser, body);
    }
    @PostMapping("/me/upload")
    public String uploadProfilePic(@AuthenticationPrincipal User currentUser, @RequestParam("profilePic") MultipartFile profilePic) throws IOException {
        return this.usersService.uploadAvatar(currentUser,profilePic);
    }
}
