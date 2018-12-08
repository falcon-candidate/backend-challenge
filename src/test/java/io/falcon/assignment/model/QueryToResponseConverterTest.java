package io.falcon.assignment.model;

import static org.junit.Assert.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.Before;
import org.junit.Test;

public class QueryToResponseConverterTest {

  private QueryToResponseConverter converter;

  @Before
  public void setUp() {
    converter = new QueryToResponseConverter();
  }

  @Test
  public void toResponse() {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    PalindromeResponse expected = new PalindromeResponse("abc", timestamp, 1);
    PalindromeResponse actual = converter.toResponse(new PalindromeQuery("abc", timestamp));
    assertEquals(expected.getContent(), expected.getContent());
    assertEquals(expected.getRequestTimeStamp(), expected.getRequestTimeStamp());
    assertEquals(expected.getLongestPalindromeSize(), actual.getLongestPalindromeSize());
  }
}
