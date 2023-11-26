package blog.platform.service;

import blog.platform.config.UserDetailsImpl;
import blog.platform.domain.User;
import blog.platform.dto.auth.SignInRequest;
import blog.platform.dto.auth.SignUpRequest;
import blog.platform.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new UserDetailsImpl(user.getId(),user.getUsername(),user.getEmail(),user.getSurname(),user.getPassword());
    }

    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    public void save(SignUpRequest sign) {
        User user = new User();
        user.setUsername(sign.getUsername());
        user.setSurname(sign.getSurname());
        user.setEmail(sign.getEmail());
        user.setPassword(hash(sign.getPassword()));
        userRepo.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }


    public boolean equalPassword(User user1,User user2){
        user2.setPassword(hash(user2.getPassword()));
        return user1.getPassword().equals(user2.getPassword());
    }

    public User getById(Long id){
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    public boolean existByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean existByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

}
