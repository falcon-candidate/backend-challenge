package io.falcon.assignment.search;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PalindromeSubstringSearchDynamicTest {

  private PalindromeSubstringSearchDynamic palindromeService;

  @Before
  public void init() {
    palindromeService = new PalindromeSubstringSearchDynamic();
  }

  @Test
  public void findMaxPalindromeLengthEmpty() {
    String palindrome = "";
    int expected = 0;
    int actual = palindromeService.findMaxPalindromeLength(palindrome);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void findMaxPalindromeLengthSingleLetter() {
    String palindrome = "a";
    int expected = 1;
    int actual = palindromeService.findMaxPalindromeLength(palindrome);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void findMaxPalindromeLengthOdd() {
    String palindrome = "aba";
    int expected = 3;
    int actual = palindromeService.findMaxPalindromeLength(palindrome);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void findMaxPalindromeLengthEven() {
    String palindrome = "aa";
    int expected = 2;
    int actual = palindromeService.findMaxPalindromeLength(palindrome);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void findMaxPalindromeComplexSubstring() {
    String palindrome = "abrakadabra";
    int expected = 3;
    int actual = palindromeService.findMaxPalindromeLength(palindrome);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void findMaxPalindromeComplexWhole() {
    String palindrome = "abrakadabra" + new StringBuilder("abrakadabra").reverse().toString();
    int expected = 22;
    int actual = palindromeService.findMaxPalindromeLength(palindrome);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void findMaxPalindromeComplexSingleLetter() {
    String palindrome = "abcdefgh";
    int expected = 1;
    int actual = palindromeService.findMaxPalindromeLength(palindrome);
    Assert.assertEquals(expected, actual);
  }

}
