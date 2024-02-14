package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.enums.Role;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.BadRequestException;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.UnauthorizedException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.NewUserDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.UserLoginDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.UsersRepository;
import MoEzwawi.GUIDANOMADI_BACKEND.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private JWTTools jwtTools;

    public User save(NewUserDTO body) {
        this.usersRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The e-mail " + user.getEmail() + " is already in use!");
        });
        User newUser = new User();
        newUser.setFirstName(body.firstName());
        newUser.setLastName(body.lastName());
        newUser.setEmail(body.email());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setAvatarUrl("https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.lastName());
        if (body.role() == null) {
            newUser.setRole(Role.USER);
        } else if (body.role().equalsIgnoreCase("ADMIN")) {
            newUser.setRole(Role.ADMIN);
        } else {
            newUser.setRole(Role.USER);
        }
        return this.usersRepository.save(newUser);
    }
    public User findByEmail(String email){
        return this.usersService.findByEmail(email);
    }
    public String authenticateUser(UserLoginDTO body) {
        User user = this.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return this.jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Wrong email or password");
        }
    }
}