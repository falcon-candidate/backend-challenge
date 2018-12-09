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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
public class PalindromeRestControllerTest {

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
  public void testRetrievePalindromesEmpty() throws JSONException {
    HttpEntity<String> entity = new HttpEntity<>("", headers);

    ResponseEntity<String> response = restTemplate.exchange(palindromeUrl,
        HttpMethod.GET, entity, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    String expected = "[]";
    JSONAssert.assertEquals(expected, response.getBody(), true);
  }

  @Test
  public void testPostQueryPalindrome() throws JSONException {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeQuery query = new PalindromeQuery("abc", timestamp);

    HttpEntity<PalindromeQuery> entity = new HttpEntity<>(query, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        palindromeUrl, HttpMethod.POST, entity, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    String expected = "{\"content\":\"abc\",\"timestamp\":\"2016-11-06 00:20:30-0500\"}";
    JSONAssert.assertEquals(expected, response.getBody(), true);
  }

  @Test
  public void testPostedQueryPalindromeStored() throws JSONException {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeQuery query = new PalindromeQuery("abc", timestamp);

    HttpEntity<PalindromeQuery> entity = new HttpEntity<>(query, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        palindromeUrl, HttpMethod.POST, entity, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    String expected = "{\"content\":\"abc\",\"timestamp\":\"2016-11-06 00:20:30-0500\"}";
    JSONAssert.assertEquals(expected, response.getBody(), true);

    ResponseEntity<String> getResponse = restTemplate.exchange(
        palindromeUrl, HttpMethod.GET, new HttpEntity<>("", headers), String.class);

    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    String expectedGet = "[{\"content\":\"abc\",\"timestamp\":\"2016-11-06 00:20:30-0500\",\"longest_palindrome_size\":1}]";
    JSONAssert.assertEquals(expectedGet, getResponse.getBody(), true);
  }

}
