package io.falcon.assignment.controller;

import io.falcon.assignment.model.PalindromeQuery;
import io.falcon.assignment.model.PalindromeResponse;
import io.falcon.assignment.model.QueryToResponseConverter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for posting palindrome queries, and retrieving palindrome responses.
 */
@RestController
public class PalindromeRestController {

  // TODO improve logging
  private static final Logger logger = LoggerFactory.getLogger(PalindromeRestController.class);

  @Autowired
  private QueryToResponseConverter queryConverter;

  // will replace this with actual repository
  private final List<PalindromeQuery> queries;

  private final String DEFAULT_QUERY_FORMAT_ERROR = "{\"error\" : \"invalid format\"}";

  public PalindromeRestController() {
    queries = new LinkedList<>();
  }

  @GetMapping("/palindrome")
  public List<PalindromeResponse> getPalindromes() {
    // TODO use pageable?
    logger.info("GET request");
    return queries.stream().map(
        query -> queryConverter.toResponse(query)
    ).collect(Collectors.toList());
  }

  @PostMapping(value = "/palindrome")
  public ResponseEntity<?> storePalindromeQuery(@Valid @RequestBody PalindromeQuery palindromeQuery,
      BindingResult result) {
    // TODO handle parsing exceptions gracefully
    if (result.hasErrors()) {
      logger.error("POST request invalid format");
      return new ResponseEntity<>(DEFAULT_QUERY_FORMAT_ERROR, HttpStatus.BAD_REQUEST);
    } else {
      logger.info("Valid POST request");
      queries.add(palindromeQuery);
      return new ResponseEntity<>(palindromeQuery, HttpStatus.OK);
    }
  }

}
