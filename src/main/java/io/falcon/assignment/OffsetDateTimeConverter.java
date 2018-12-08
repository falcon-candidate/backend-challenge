package io.falcon.assignment;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Class for converting {@link OffsetDateTime} to {@link String} before persisting, and vice versa.
 * <p>Note: this is a workaround for losing the time offset when persisting the OffsetDateTime as a
 * timestamp.</p>
 */
@Converter(autoApply = true)
public class OffsetDateTimeConverter implements AttributeConverter<OffsetDateTime, String> {

  @Override
  public String convertToDatabaseColumn(OffsetDateTime offsetDateTime) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXXX");
    return offsetDateTime.format(dateTimeFormatter);
  }

  @Override
  public OffsetDateTime convertToEntityAttribute(String date) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXXX");
    return OffsetDateTime.parse(date, dateTimeFormatter);
  }
}
