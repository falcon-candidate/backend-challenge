package io.falcon.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.falcon.assignment.serialization.OffsetDateTimeDeserializer;
import io.falcon.assignment.serialization.OffsetDateTimeSerializer;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Represents a query submitted via REST API, with payload to find longest palindrome substring in.
 */
@Entity
@Table(name = "palindromes")
public class PalindromeQuery {

  @Id
  @GeneratedValue
  @JsonIgnore
  private int id;

  // TODO match json property and column name
  @Column(name = "text")
  @JsonProperty("content")
  @NotNull
  @Pattern(regexp = "^[A-Za-z]+$")
  @Size(min = 1, max = 100)
  // Ensure that content consists of letters only and is not null when we parse a POST request
  private String content;

  @Column(name = "request_time_stamp")
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

