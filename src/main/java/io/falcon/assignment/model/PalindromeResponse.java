package io.falcon.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.falcon.assignment.serialization.OffsetDateTimeDeserializer;
import io.falcon.assignment.serialization.OffsetDateTimeSerializer;
import java.time.OffsetDateTime;

/**
 * Represents a response retrieved via REST api, with original query payload, timestamp, and maximum
 * palindrome substring size.
 */
public class PalindromeResponse {

  @JsonProperty("content")
  private final String content;

  @JsonProperty("timestamp")
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  private final OffsetDateTime requestTimeStamp;

  @JsonProperty("longest_palindrome_size")
  private final int longestPalindromeSize;

  public PalindromeResponse(String content, OffsetDateTime requestTimeStamp,
      int longestPalindromeSize) {
    this.content = content;
    this.requestTimeStamp = requestTimeStamp;
    this.longestPalindromeSize = longestPalindromeSize;
  }

  public String getContent() {
    return content;
  }

  public int getLongestPalindromeSize() {
    return longestPalindromeSize;
  }

  public OffsetDateTime getRequestTimeStamp() {
    return requestTimeStamp;
  }

}
