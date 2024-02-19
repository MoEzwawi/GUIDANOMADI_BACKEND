package MoEzwawi.GUIDANOMADI_BACKEND.controllers;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.BadRequestException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.users.*;
import MoEzwawi.GUIDANOMADI_BACKEND.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // status code 201
    public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Errors in payload!");
        } else {
            User newUser = authService.save(body);
            return new NewUserResponseDTO(newUser.getId());
        }
    }
    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody @Validated UserLoginDTO body) {
        String accessToken = this.authService.authenticateUser(body);
        return new UserLoginResponseDTO(
                accessToken
        );
    }
}
