package io.falcon.assignment.search;

import io.falcon.assignment.model.PalindromeQuery;
import io.falcon.assignment.model.PalindromeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service mapping {@link PalindromeQuery} objects to {@link PalindromeResponse} objects. <p>This
 * includes computing the length of the max. palindrome substring.</p>
 */
@Component
public class PalindromeSearchService {

  @Autowired
  PalindromeSubstringSearch palindromeSubstringSearch;

  /**
   * Turns query into response by computing max palindrome substring size and including it in the
   * response.
   *
   * @param query query to generate response from
   * @return response with added max palindrome substring size
   */
  public PalindromeResponse toResponse(PalindromeQuery query) {
    int longestPalindromeSize = palindromeSubstringSearch
        .findMaxPalindromeLength(query.getContent());
    return new PalindromeResponse(query.getContent(), query.getRequestTimeStamp(),
        longestPalindromeSize);
  }

}
