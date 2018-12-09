package io.falcon.assignment.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.assignment.model.PalindromeQuery;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Broadcast service for sending queries to listening Websocket clients. <p>Exposes {@link
 * #broadcast(PalindromeQuery)} method which the rest controller can call to broadcast
 * messages.</p>
 */
@Component
public class PalindromeBroadcastService extends TextWebSocketHandler {

  private static final Logger logger = LoggerFactory.getLogger(PalindromeBroadcastService.class);

  private final List<WebSocketSession> sessions;

  @Autowired
  private ObjectMapper jacksonMapper;

  public PalindromeBroadcastService() {
    sessions = new CopyOnWriteArrayList<>();
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
    // ignore incoming messages
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    InetSocketAddress clientAddress = session.getRemoteAddress();

    logger.info("Accepted connection from: {}:{}", clientAddress.getHostString(),
        clientAddress.getPort());

    sessions.add(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    logger.info("Connection closed by {}:{}", session.getRemoteAddress().getHostString(),
        session.getRemoteAddress().getPort());

    super.afterConnectionClosed(session, status);

    sessions.remove(session);
  }

  /**
   * Broadcasts a palindrome query to all listening clients.
   *
   * @param query palindrome query to broadcast
   */
  public void broadcast(PalindromeQuery query) throws IOException {
    logger.info("Broadcasting palindrome query");
    String payload = jacksonMapper.writeValueAsString(query);
    for (WebSocketSession webSocketSession : sessions) {
      webSocketSession.sendMessage(new TextMessage(payload));
    }
  }

}
