package EduStore_Pet.Pet_pr.controller;

import EduStore_Pet.Pet_pr.DTO.JwtRequest;
import EduStore_Pet.Pet_pr.DTO.RegistrationUserDTO;
import EduStore_Pet.Pet_pr.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "authentication method",
            description = "This method authenticates a user by validating their credentials" +
                    "and returns a JWT token if the authentication is successful. " +
                    "The token can be used for accessing secured endpoints in the application."
    )
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

    @Operation(
            summary = "User registration method",
            description = "This method registers a new user by accepting their registration details" +
                    "and saving the user information in the system. " +
                    "After successful registration, the user can authenticate and access secured endpoints."
    )
    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO){
        return authService.createNewUser(registrationUserDTO);
    }
}
