package io.falcon.assignment.search;

import org.springframework.stereotype.Component;

/**
 * Longest palindrome substring search based on dynamic programming.
 */
@Component
public class PalindromeSubstringSearchDynamic implements PalindromeSubstringSearch {

  /**
   * {@inheritDoc}
   *
   * <p>Concrete algorithm works by iterating over string, and at each position treats it as the
   * center of a palindrome, and checks how far the palindrome stretches by expanding outward in
   * both directions from it.</p>
   *
   * <p>Has O(n^{2}) runtime complexity.</p>
   *
   * <p>Based on <a href="https://www.geeksforgeeks.org/longest-palindromic-substring-set-2/">https://www.geeksforgeeks.org/longest-palindromic-substring-set-2/.</a></p>
   */
  @Override
  public int findMaxPalindromeLength(String toSearchIn) {
    int totalLength = toSearchIn.length();

    // We can exit early if input is single character or empty
    if (toSearchIn.length() < 2) {
      return toSearchIn.length();
    }

    // We treat string as case-insensitive
    toSearchIn = toSearchIn.toLowerCase();
    int maxLength = 1;

    // For each position, treat it as the center of a palindrome, and check how far the palindrome
    // stretches by expanding outward in both directions from it
    for (int position = 1; position < totalLength; ++position) {
      // Find the length of the even palindrome with current center, and update max length
      int evenLength = getLengthOfPalindromeAt(position, toSearchIn, SearchMode.Even);
      maxLength = Math.max(maxLength, evenLength);

      // Find the length of the odd palindrome with current center
      int oddLength = getLengthOfPalindromeAt(position, toSearchIn, SearchMode.Odd);
      maxLength = Math.max(maxLength, oddLength);
    }

    return maxLength;
  }

  /**
   * Checks how long the palindrome centered around the given position is. <p>Works by expanding in
   * both directions from the given position, and checking if the current character on the left is
   * equal to the current character on the right.</p>
   *
   * @param position center of the palindrome to search from
   * @param toSearchIn string to search in
   * @param mode flag indicating if we are looking for an even or an odd palindrome at this
   * position
   * @return length of currently longest palindrome after search
   */
  private int getLengthOfPalindromeAt(int position, String toSearchIn,
      SearchMode mode) {
    int palindromeLength = 1;
    int leftPosition = position - 1;
    
    // if we are looking for an odd palindrome, the center is at the position, otherwise it is
    // "between" the given position and the next
    int rightPosition = (mode == SearchMode.Odd) ? position : position + 1;

    int totalLength = toSearchIn.length();
    // we move one position to the left and the right in step, until we have hit either end of the
    // string, or the left character is not equal to the right character (i.e., what we have isn't a
    // palindrome anymore)
    while (leftPosition >= 0 && rightPosition < totalLength
        && toSearchIn.charAt(leftPosition) == toSearchIn.charAt(rightPosition)) {
      // we have found a longer palindrome so update
      if (rightPosition - leftPosition + 1 > palindromeLength) {
        palindromeLength = rightPosition - leftPosition + 1;
      }
      --leftPosition;
      ++rightPosition;
    }

    return palindromeLength;
  }

  /**
   * Indicates if we are searching for an odd or an even palindrome in {@link
   * #findMaxPalindromeLength(String)}.
   */
  private enum SearchMode {
    Odd, Even
  }

}
