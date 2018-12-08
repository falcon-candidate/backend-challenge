package io.falcon.assignment.repository;

import io.falcon.assignment.model.PalindromeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalindromeQueryRepository extends JpaRepository<PalindromeQuery, Integer> {

}
