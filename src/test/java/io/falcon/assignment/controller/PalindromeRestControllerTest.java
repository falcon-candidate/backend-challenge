package io.falcon.assignment.controller;

import static org.junit.Assert.assertEquals;

import io.falcon.assignment.Application;
import io.falcon.assignment.model.PalindromeQuery;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.json.JSONException;
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
public class PalindromeRestControllerTest {

  @LocalServerPort
  private int port;

  TestRestTemplate restTemplate = new TestRestTemplate();
  HttpHeaders headers = new HttpHeaders();

  @Test
  public void testRetrievePalindromesEmpty() throws JSONException {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/palindrome"),
        HttpMethod.GET, entity, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    String expected = "[]";
    JSONAssert.assertEquals(expected, response.getBody(), true);
  }

  @Test
  public void testQueryPalindrome() throws JSONException {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeQuery query = new PalindromeQuery("abc", timestamp);

    HttpEntity<PalindromeQuery> entity = new HttpEntity<>(query, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/palindrome"),
        HttpMethod.POST, entity, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    String expected = "{\"content\":\"abc\",\"timestamp\":\"2016-11-06 00:20:30-0500\"}";
    JSONAssert.assertEquals(expected, response.getBody(), true);
  }

  @Test
  public void testQueryPalindromeStored() throws JSONException {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeQuery query = new PalindromeQuery("abc", timestamp);

    HttpEntity<PalindromeQuery> entity = new HttpEntity<>(query, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        createURLWithPort("/palindrome"),
        HttpMethod.POST, entity, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    String expected = "{\"content\":\"abc\",\"timestamp\":\"2016-11-06 00:20:30-0500\"}";
    JSONAssert.assertEquals(expected, response.getBody(), true);

    ResponseEntity<String> getResponse = restTemplate.exchange(
        createURLWithPort("/palindrome"),
        HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    String expectedGet = "[{\"content\":\"abc\",\"timestamp\":\"2016-11-06 00:20:30-0500\",\"longest_palindrome_size\":1}]";
    System.out.println(getResponse.getBody());
    JSONAssert.assertEquals(expectedGet, getResponse.getBody(), true);
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}
