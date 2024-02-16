package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.enums.Role;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.BadRequestException;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.UpdateEmailDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.UpdateNameAndSurnameDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.UpdatePasswordDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.UsersRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary cloudinary;
    public Page<User> getAllUsers(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return usersRepository.findAll(pageable);
    }
    public User findById(UUID id) {
        return this.usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public User findByEmail(String email) throws NotFoundException {
        return this.usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("No user found for email " + email));
    }
    public User updateNameAndSurname(User currentUser, UpdateNameAndSurnameDTO body){
        currentUser.setFirstName(body.firstName());
        currentUser.setLastName(body.lastName());
        return this.usersRepository.save(currentUser);
    }
    public User changeEmail(User currentUser, UpdateEmailDTO body){
        this.usersRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The e-mail " + user.getEmail() + " is already in use!");
        });
        currentUser.setEmail(body.email());
        return this.usersRepository.save(currentUser);
    }
    public void changePassword(User currentUser, UpdatePasswordDTO body){
        currentUser.setPassword(bcrypt.encode(body.password()));
        this.usersRepository.save(currentUser);
    }
    public User findByIdAndPromote(UUID id){
        User found = this.findById(id);
        if (found.getRole() == Role.USER){
            found.setRole(Role.ADMIN);
            this.usersRepository.save(found);
        }
        return found;
    }
    public User findByIdAndDowngrade(UUID id){
        User found = this.findById(id);
        if (found.getRole() == Role.ADMIN){
            found.setRole(Role.USER);
            this.usersRepository.save(found);
        }
        return found;
    }
    public String uploadAvatar(User currentUser,MultipartFile file) throws IOException {
        String url = (String) this.cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");
        currentUser.setAvatarUrl(url);
        this.usersRepository.save(currentUser);
        return url;
    }
}
