package blog.platform.config;

import blog.platform.config.JWT.JwtRequestFilter;
import blog.platform.domain.User;
import blog.platform.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private UserService userService;
    private JwtRequestFilter jwtRequestFilter;
    public SecurityConfig(UserService userService,JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Bean
    public AuthenticationManager authenticationManager() {
    return (authentication) -> {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (!userService.existByUsername(username)) throw new BadCredentialsException("Такого пользователя не существует");
        User user1 = userService.getUserByUsername(username);
        User user2 = new User(username,password);
        if(userService.equalPassword(user1,user2)) return new UsernamePasswordAuthenticationToken(user2.getUsername(),user2.getPassword());
        else throw new BadCredentialsException("Не правильный пароль или имя пользователя");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .cors(cors -> cors
                        .configurationSource(request -> configuration()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    private CorsConfiguration configuration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return config;
    }
}
