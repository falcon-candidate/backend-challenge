package io.falcon.assignment.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom deserializer for {@link OffsetDateTime} to correctly account for time offset.
 */
public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

  @Override
  public OffsetDateTime deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXXX");
    String date = jsonParser.getText();
    return OffsetDateTime.parse(date, dateTimeFormatter);
  }

}
