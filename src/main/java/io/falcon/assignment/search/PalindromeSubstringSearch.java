package io.falcon.assignment.search;

/**
 * Interface all palindrome search algorithms must implement. <p>See {@link
 * #findMaxPalindromeLength(String)} for more info.</p>
 */
public interface PalindromeSubstringSearch {

  /**
   * Computes the length of the longest palindrome substring. <p>A palindrome is a string that reads
   * the same forward and backward (see <a href="https://en.wikipedia.org/wiki/Palindrome">https://en.wikipedia.org/wiki/Palindrome</a>).</p>
   *
   * @param toSearchIn String to search longest palindrome in. String must consist of letters only
   * and is treated as case-insensitive.
   * @return length of longest palindrome substring
   */
  int findMaxPalindromeLength(String toSearchIn);
}
