package io.falcon.assignment.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

public class PalindromeQueryValidationTest {

  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private DateTimeFormatter dateTimeFormatter;
  private Validator validator;

  @Before
  public void setup() {
    dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXXX");
    validator = factory.getValidator();
  }

  @Test
  public void correctPalindromeQueryValidates() {
    String timeStampString = "2018-10-09 00:12:12+0001";
    OffsetDateTime offsetDateTime = OffsetDateTime.parse(timeStampString, dateTimeFormatter);
    PalindromeQuery query = new PalindromeQuery("aba", offsetDateTime);
    validator = factory.getValidator();
    Set<ConstraintViolation<PalindromeQuery>> violations = validator.validate(query);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void incorrectPalindromeQueryContentBreaks() {
    String timeStampString = "2018-10-09 00:12:12+0001";
    OffsetDateTime offsetDateTime = OffsetDateTime.parse(timeStampString, dateTimeFormatter);
    PalindromeQuery query = new PalindromeQuery("aba1", offsetDateTime);
    validator = factory.getValidator();
    // TODO improve
    Set<ConstraintViolation<PalindromeQuery>> violations = validator.validate(query);
    assertFalse(violations.isEmpty());
  }

  @Test
  public void incorrectPalindromeQueryNullTimeStampBreaks() {
    PalindromeQuery query = new PalindromeQuery("aba", null);
    validator = factory.getValidator();
    // TODO improve
    Set<ConstraintViolation<PalindromeQuery>> violations = validator.validate(query);
    assertFalse(violations.isEmpty());
  }
}
