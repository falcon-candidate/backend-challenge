package io.falcon.assignment;

import io.falcon.assignment.model.PalindromeQuery;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Various helper methods for working with {@link PalindromeQuery} instances during testing.
 */
public class PalindromeQueryUtils {

  private PalindromeQueryUtils() {
  }

  /**
   * Creates a valid, fixed {@link PalindromeQuery} instance.
   */
  public static PalindromeQuery getValidQuery() {
    OffsetDateTime timestamp = OffsetDateTime.of(2016, 11, 6,
        0, 20, 30, 1000,
        ZoneOffset.ofHours(-5));
    return new PalindromeQuery("abc", timestamp);
  }

}
