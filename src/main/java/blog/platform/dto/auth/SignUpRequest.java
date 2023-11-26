package blog.platform.dto.auth;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String surname;
    private String email;
    private String password;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String surname, String email, String password) {
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
}
