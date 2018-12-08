package io.falcon.assignment.model;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

@JsonTest
@RunWith(SpringRunner.class)
public class PalindromeQuerySerializationTest {

  @Autowired
  private JacksonTester<PalindromeQuery> json;

  private DateTimeFormatter dateTimeFormatter;

  @Before
  public void setup() {
    dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXXX");
  }

  @Test
  public void correctQuerySerializes() throws IOException {
    String content = "abra";
    String timeStampString = "2018-10-09 00:12:12+0001";
    OffsetDateTime offsetDateTime = OffsetDateTime.parse(timeStampString, dateTimeFormatter);
    PalindromeQuery correctQuery = new PalindromeQuery(content, offsetDateTime);

    final JsonContent<PalindromeQuery> actual = this.json.write(correctQuery);

    assertThat(actual)
        .extractingJsonPathStringValue("@.content")
        .isEqualTo(content);

    assertThat(actual)
        .extractingJsonPathStringValue("@.timestamp")
        .isEqualTo(timeStampString);
  }

  @Test
  public void correctQueryDeserializes() throws IOException {
    String content = "abra";
    String timeStampString = "2018-10-09 00:12:12+0001";
    String jsonPayload = String
        .format("{\"content\": \"%s\", \"timestamp\": \"%s\"}", content, timeStampString);
    final PalindromeQuery actual = this.json.parseObject(jsonPayload);

    OffsetDateTime expectedTimeStamp = OffsetDateTime.parse(timeStampString, dateTimeFormatter);

    assertThat(actual.getContent())
        .isEqualTo(content);
    assertThat(actual.getRequestTimeStamp())
        .isEqualTo(expectedTimeStamp);
  }

  @Test(expected = JsonMappingException.class)
  public void incorrectDateQueryFails() throws IOException {
    String content = "abra";
    // timestamp missing offset
    String timeStampString = "2018-10-09 00:12:12";
    String jsonPayload = String
        .format("{\"content\": \"%s\", \"timestamp\": \"%s\"}", content, timeStampString);
    this.json.parseObject(jsonPayload);
  }

}
