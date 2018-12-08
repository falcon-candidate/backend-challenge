package io.falcon.assignment.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom serializer for {@link OffsetDateTime} to correctly account for time offset.
 */
public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

  @Override
  public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXXX");
    String str = offsetDateTime.format(dateTimeFormatter);
    jsonGenerator.writeString(str);
  }

}
