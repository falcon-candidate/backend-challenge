package io.falcon.assignment.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class PalindromeWebSocketConfigurer implements WebSocketConfigurer {

  @Autowired
  private PalindromeBroadcastService broadcastService;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    // Websocket clients can connect on via /subscribe_ws endpoint
    registry.addHandler(broadcastService, "/subscribe_ws");
  }

}
