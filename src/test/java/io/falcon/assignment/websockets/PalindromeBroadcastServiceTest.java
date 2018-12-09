package io.falcon.assignment.websockets;

import static org.junit.Assert.assertEquals;

import io.falcon.assignment.Application;
import io.falcon.assignment.model.PalindromeQuery;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PalindromeBroadcastServiceTest {

  @LocalServerPort
  private int port;

  @Autowired
  PalindromeBroadcastService broadcastService;

  @Test
  public void testMessageBroadcast()
      throws ExecutionException, InterruptedException, IOException, TimeoutException {
    WebSocketClient client = new StandardWebSocketClient();
    CompletableFuture<String> completableFuture = new CompletableFuture<>();
    final SimpleClientWebSocketHandler handler = new SimpleClientWebSocketHandler(
        completableFuture);
    ListenableFuture<WebSocketSession> futureSession = client
        .doHandshake(handler, "ws://localhost:" + port + "/subscribe_ws");
    futureSession.get();
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeQuery query = new PalindromeQuery("abc", timestamp);
    broadcastService.broadcast(query);
    String actual = completableFuture.get(42, TimeUnit.SECONDS);
    String expected = "{\"content\":\"abc\",\"timestamp\":\"2016-11-06 00:20:30-0500\"}";
    assertEquals(expected, actual);
  }
  
}
