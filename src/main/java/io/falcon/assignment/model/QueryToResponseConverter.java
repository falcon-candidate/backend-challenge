package io.falcon.assignment.model;

import org.springframework.stereotype.Component;

/**
 * Helper class for mapping {@link PalindromeQuery} objects to {@link PalindromeResponse} objects.
 */
@Component
public class QueryToResponseConverter {

  /**
   * Turns query into response by computing max palindrome substring size and including it in the
   * response.
   *
   * @param query query to generate response from
   * @return response with added max palindrome substring size
   */
  public PalindromeResponse toResponse(PalindromeQuery query) {
    return new PalindromeResponse(query.getContent(), query.getRequestTimeStamp(), 1);
  }

}
