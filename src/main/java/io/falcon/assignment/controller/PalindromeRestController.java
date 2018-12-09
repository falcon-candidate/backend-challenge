package io.falcon.assignment.controller;

import io.falcon.assignment.exception.IllegalPalindromeQueryException;
import io.falcon.assignment.model.PalindromeQuery;
import io.falcon.assignment.model.PalindromeResponse;
import io.falcon.assignment.search.PalindromeSearchService;
import io.falcon.assignment.repository.PalindromeQueryRepository;
import io.falcon.assignment.websockets.PalindromeBroadcastService;
import java.io.IOException;
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
 * REST API for posting palindrome queries, and retrieving palindrome responses. <p>Uses {@link
 * PalindromeSearchService} to find maximum palindrome substring lengths, {@link
 * PalindromeQueryRepository} to persist queries, and {@link PalindromeBroadcastService} to
 * broadcast incoming queries to listening Websocket clients.</p>
 */
@RestController
public class PalindromeRestController {

  private static final Logger logger = LoggerFactory.getLogger(PalindromeRestController.class);

  @Autowired
  private PalindromeSearchService palindromeSearchService;

  @Autowired
  private PalindromeQueryRepository palindromeQueryRepo;

  @Autowired
  private PalindromeBroadcastService broadcastService;

  @GetMapping("/palindrome")
  public List<PalindromeResponse> getPalindromes() {
    logger.info("GET request");
    return palindromeQueryRepo.findAll().stream().map(
        query -> palindromeSearchService.toResponse(query)
    ).collect(Collectors.toList());
  }

  @PostMapping(value = "/palindrome")
  public ResponseEntity<?> storePalindromeQuery(@Valid @RequestBody PalindromeQuery palindromeQuery,
      BindingResult result) throws IllegalPalindromeQueryException {
    if (result.hasErrors()) {
      logger.error("POST request with invalid format");
      throw new IllegalPalindromeQueryException();
    } else {
      logger.info("Valid POST request");
      palindromeQueryRepo.save(palindromeQuery);
      try {
        broadcastService.broadcast(palindromeQuery);
      } catch (IOException e) {
        logger.error("Broadcast failed ", e);
      }
      return new ResponseEntity<>(palindromeQuery, HttpStatus.OK);
    }
  }

}
