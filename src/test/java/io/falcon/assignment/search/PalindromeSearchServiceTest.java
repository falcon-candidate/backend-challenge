package io.falcon.assignment.search;

import static org.junit.Assert.*;

import io.falcon.assignment.model.PalindromeQuery;
import io.falcon.assignment.model.PalindromeResponse;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PalindromeSubstringSearchDynamic.class,
    PalindromeSearchService.class})
public class PalindromeSearchServiceTest {

  @Autowired
  private PalindromeSearchService palindromeSearchService;

  @Test
  public void toResponse() {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeResponse expected = new PalindromeResponse("abc", timestamp, 1);
    PalindromeResponse actual = palindromeSearchService
        .toResponse(new PalindromeQuery("abc", timestamp));
    assertEquals(expected.getContent(), expected.getContent());
    assertEquals(expected.getRequestTimeStamp(), expected.getRequestTimeStamp());
    assertEquals(expected.getLongestPalindromeSize(), actual.getLongestPalindromeSize());
  }
}
