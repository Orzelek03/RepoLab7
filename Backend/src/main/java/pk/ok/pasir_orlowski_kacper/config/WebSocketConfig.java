package pk.ok.pasir_orlowski_kacper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // Zmieniamy z EnableWebSocketMessageBroker na podstawowy protokół WebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GroupNotificationHandler groupNotificationHandler;

    public WebSocketConfig(GroupNotificationHandler groupNotificationHandler) {
        this.groupNotificationHandler = groupNotificationHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Rejestrujemy endpoint podany w kontrakcie laboratorium i włączamy CORS
        registry.addHandler(groupNotificationHandler, "/ws/group-notifications")
                .setAllowedOrigins("*");
    }
}