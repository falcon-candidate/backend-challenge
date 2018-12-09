package io.falcon.assignment.websockets;

import java.util.concurrent.CompletableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SimpleClientWebSocketHandler extends TextWebSocketHandler {

  private final CompletableFuture<String> palindromeQuery;

  public SimpleClientWebSocketHandler(CompletableFuture<String> palindromeQuery) {
    this.palindromeQuery = palindromeQuery;
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    palindromeQuery.complete(message.getPayload());
  }

}
