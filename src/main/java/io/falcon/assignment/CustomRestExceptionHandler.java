package io.falcon.assignment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  // TODO
  private final String DEFAULT_QUERY_FORMAT_ERROR = "{\"error\" : \"invalid format\"}";

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
    return new ResponseEntity<>(DEFAULT_QUERY_FORMAT_ERROR, HttpStatus.BAD_REQUEST);
  }

}
