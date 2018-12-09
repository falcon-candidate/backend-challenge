package io.falcon.assignment.controller;

import static org.junit.Assert.assertEquals;

import io.falcon.assignment.Application;
import io.falcon.assignment.model.PalindromeQuery;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PalindromeRestControllerNegativeTest {

  @LocalServerPort
  private int port;

  private String palindromeUrl;
  private TestRestTemplate restTemplate = new TestRestTemplate();
  private HttpHeaders headers = new HttpHeaders();

  @Before
  public void setUp() {
    palindromeUrl = String.format("http://localhost:%s/palindrome", port);
  }

  @Test
  public void testRetrievePalindromesNotFound() {
    HttpEntity<String> entity = new HttpEntity<>("", headers);
    String badUrl = String.format("http://localhost:%s/bad", port);
    ResponseEntity<String> response = restTemplate.exchange(badUrl,
        HttpMethod.GET, entity, String.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

}
