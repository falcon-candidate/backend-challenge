package io.falcon.assignment.controller;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.assignment.Application;
import io.falcon.assignment.model.PalindromeQuery;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PalindromeRestControllerNegativeTest {

  // TODO these should be unit tests

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

  @Test
  public void testBadPostContentNotLetters() throws JSONException {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeQuery query = new PalindromeQuery("abc1", timestamp);

    HttpEntity<PalindromeQuery> entity = new HttpEntity<>(query, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        palindromeUrl, HttpMethod.POST, entity, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    String expected = "{\"error\" : \"something went wrong\"}";
    JSONAssert.assertEquals(expected, response.getBody(), true);
  }

  @Test
  public void testBadPostBadDate() throws JSONException, JsonProcessingException {
    Map<String, String> map= new HashMap<>();
    map.put("content", "abc");
    map.put("timestamp", "2018-10-09 00:12:12"); // missing offset
    ObjectMapper mapperObj = new ObjectMapper();

    String json = mapperObj.writeValueAsString(map);

    HttpHeaders localHeaders = new HttpHeaders();
    localHeaders.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> entity = new HttpEntity<>(json, localHeaders);
    ResponseEntity<String> response = restTemplate.exchange(
        palindromeUrl, HttpMethod.POST, entity, String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    String expected = "{\"error\" : \"something went wrong\"}";
    JSONAssert.assertEquals(expected, response.getBody(), true);
  }

}
