package blog.platform.controller;

import blog.platform.config.JWT.JwtCore;
import blog.platform.config.UserDetailsImpl;
import blog.platform.domain.User;
import blog.platform.dto.auth.SignInRequest;
import blog.platform.dto.auth.SignUpRequest;
import blog.platform.dto.responses.JwtResponse;
import blog.platform.dto.responses.Message;
import blog.platform.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@CrossOrigin
public class Auth {
    private  UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtCore jwtCore;



    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest sign) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sign.getUsername(), sign.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new Message("Не правильный логин или пароль"));
        }
        UserDetailsImpl userDetails = userService.loadUserByUsername(sign.getUsername());
        String token = jwtCore.generateToken(userDetails);
        return  ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest sign) {
        if (userService.existByUsername(sign.getUsername())) {
            return ResponseEntity.badRequest().body("Пользователь уже существует");
        }
        userService.save(sign);
        UserDetailsImpl userDetails = userService.loadUserByUsername(sign.getUsername());
        String token = jwtCore.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
