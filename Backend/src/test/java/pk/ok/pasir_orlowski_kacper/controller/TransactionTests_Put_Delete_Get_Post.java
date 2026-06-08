package pk.ok.pasir_orlowski_kacper.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionTests_Put_Delete_Get_Post{

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testDostepDoWlasnychDanych() throws Exception {

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testBladDostepuDoInnych() throws Exception {
        mockMvc.perform(get("/api/transactions/3"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testBrakLogowania() throws Exception {
        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testZaktualizujTransakcjeWlasna() throws Exception {
        String jsonUpdate = """
        {
            "amount": 2500.0,
            "type": "EXPENSE",
            "tags": "rachunki",
            "notes": "Zaktualizowany czynsz"
        }
        """;


        mockMvc.perform(put("/api/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testZaktualizujTransakcjeKacpraJakoOrzel() throws Exception {
        String jsonUpdate = "{\"amount\": 100.0, \"type\": \"INCOME\"}";

        mockMvc.perform(put("/api/transactions/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isForbidden());
    }

    @Test
    void testZaktualizujBezLogowania() throws Exception {
        mockMvc.perform(put("/api/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testUsunTransakcjeWlasna() throws Exception {

        mockMvc.perform(delete("/api/transactions/5"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testUsunTransakcjeMarcinaJakoOrzel() throws Exception {

        mockMvc.perform(delete("/api/transactions/11"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUsunBezLogowania() throws Exception {

        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testDodajTransakcjeOrzel() throws Exception {
        // 2. POST - DodajTransakcje - Orzel (income)
        String json = """
        {
            "amount": 1500.0,
            "type": "INCOME",
            "tags": "wynagrodzenie",
            "notes": "Przelew za marzec",
            "timestamp": "2026-04-08T10:00:00"
        }
        """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()); // Powinno zapisać transakcję dla orzel@test.pl
    }

    @Test
    void testDodajBezLogowania() throws Exception {
        // 2. POST - Dodaj BEZ LOGOWANIA (Błąd 401/403)
        String json = "{\"amount\": 100.0, \"type\": \"EXPENSE\"}";

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden()); // Spring Security zablokuje dostęp anonimowy
    }

    @Test
    @WithMockUser(username = "orzel1@example.com")
    void testDodajBledneDane() throws Exception {
        // 2. POST - Dodaj Błędne dane (walidacja DTO)
        // Kwota 0.0 jest niedozwolona przez @DecimalMin w TransactionDTO
        String jsonInvalid = """
        {
            "amount": -10.0,
            "type": "INCOME",
            
        }
        """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalid))
                .andExpect(status().isBadRequest());
    }
}