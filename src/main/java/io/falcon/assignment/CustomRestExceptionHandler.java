package io.falcon.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
public class CustomRestExceptionHandler {

  // TODO
  private final String DEFAULT_QUERY_FORMAT_ERROR = "{\"error\" : \"something went wrong\"}";

  private static final Logger logger = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
    logger.error("Bad request caused exception ", ex);
    return new ResponseEntity<>(DEFAULT_QUERY_FORMAT_ERROR, HttpStatus.BAD_REQUEST);
  }

}
