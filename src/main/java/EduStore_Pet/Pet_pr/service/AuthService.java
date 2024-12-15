package EduStore_Pet.Pet_pr.service;

import EduStore_Pet.Pet_pr.DTO.JwtRequest;
import EduStore_Pet.Pet_pr.DTO.JwtResponse;
import EduStore_Pet.Pet_pr.DTO.UserDTO;
import EduStore_Pet.Pet_pr.entity.User;
import EduStore_Pet.Pet_pr.DTO.RegistrationUserDTO;
import EduStore_Pet.Pet_pr.exception.AppError;
import EduStore_Pet.Pet_pr.repo.UserRepository;
import EduStore_Pet.Pet_pr.utils.JwtTokenUtils;
import EduStore_Pet.Pet_pr.utils.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private KafkaSender kafkaSender;

    public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(RegistrationUserDTO registrationUserDTO) {
        if (userService.findByUsername(registrationUserDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDTO);
        kafkaSender.sendUserRegistrationNotification("User " + user.getEmail() + " has registered.");
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail()));
    }
}
