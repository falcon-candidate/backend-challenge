package io.falcon.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.falcon.assignment.serialization.OffsetDateTimeDeserializer;
import io.falcon.assignment.serialization.OffsetDateTimeSerializer;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Represents a query submitted via REST api, with payload to find longest palindrome substring in.
 */
public class PalindromeQuery {

  @JsonProperty("content")
  @NotNull
  @Pattern(regexp = "^[A-Za-z]+$")
  // Ensure that content consists of letters only and is not null when we convert from JSON
  private String content;

  @JsonProperty("timestamp")
  @NotNull
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  // Ensure timestamp is not null and is parsed using custom (de)serializer to account for offset
  private OffsetDateTime requestTimeStamp;

  public PalindromeQuery() {

  }

  /**
   * Creates new {@link PalindromeQuery}.
   *
   * @param content string to search for longest palindrome substring
   * @param requestTimeStamp timestamp (with offset) specified by query submitter
   */
  public PalindromeQuery(
      @NotNull @Pattern(regexp = "^[A-Za-z]+$") String content,
      @NotNull OffsetDateTime requestTimeStamp) {
    this.content = content;
    this.requestTimeStamp = requestTimeStamp;
  }

  public String getContent() {
    return content;
  }

  public OffsetDateTime getRequestTimeStamp() {
    return requestTimeStamp;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setRequestTimeStamp(OffsetDateTime requestTimeStamp) {
    this.requestTimeStamp = requestTimeStamp;
  }
}

