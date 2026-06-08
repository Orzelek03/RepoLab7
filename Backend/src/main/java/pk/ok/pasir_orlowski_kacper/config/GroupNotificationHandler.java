package pk.ok.pasir_orlowski_kacper.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pk.ok.pasir_orlowski_kacper.dto.GroupNotificationDTO;
import pk.ok.pasir_orlowski_kacper.security.JwtUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GroupNotificationHandler extends TextWebSocketHandler {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Mapa przechowująca aktywne sesje użytkowników (Klucz: Email, Wartość: Sesja)
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public GroupNotificationHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Wyciągamy token z query stringa URL: ?token=...
        String query = session.getUri().getQuery();
        if (query != null && query.contains("token=")) {
            String token = query.split("token=")[1];

            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractUsername(token);
                sessions.put(email, session);
                System.out.println("WebSocket połączony dla użytkownika: " + email);
                return;
            }
        }
        // Jeśli token jest niepoprawny, zamykamy połączenie
        session.close(CloseStatus.NOT_ACCEPTABLE);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }

    // Metoda, którą wywołamy w serwisie, aby wysłać powiadomienie do konkretnego użytkownika
    public void sendNotificationToUser(String email, GroupNotificationDTO notification) {
        WebSocketSession session = sessions.get(email);
        if (session != null && session.isOpen()) {
            try {
                String json = objectMapper.writeValueAsString(notification);
                session.sendMessage(new TextMessage(json));
            } catch (Exception e) {
                System.err.println("Nie udało się wysłać powiadomienia WebSocket: " + e.getMessage());
            }
        }
    }
}