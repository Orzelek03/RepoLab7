package pk.ok.pasir_orlowski_kacper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pk.ok.pasir_orlowski_kacper.dto.LoginDto;
import pk.ok.pasir_orlowski_kacper.dto.UserDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterAndLoginUser() throws Exception {
        // 1. Rejestracja (Scenariusz pozytywny) [cite: 299]
        UserDTO user = new UserDTO();
        user.setUsername("Jan");
        user.setEmail("jan@test.pl");
        user.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        LoginDto login = new LoginDto();
        login.setEmail("jan@test.pl");
        login.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists()); // Sprawdza czy wrócił token [cite: 292]
    }

    @Test
    void shouldReturn401ForWrongPassword() throws Exception {
        // Logowanie z błędnym hasłem [cite: 304]
        LoginDto login = new LoginDto();
        login.setEmail("jan@test.pl");
        login.setPassword("zle_haslo");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }
}