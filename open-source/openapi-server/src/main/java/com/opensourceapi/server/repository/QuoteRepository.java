package com.opensourceapi.server.repository;

import com.opensourceapi.server.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByAuthorContainingIgnoreCase(String author);
    List<Quote> findByCategoryIgnoreCase(String category);
    
    @Query(value = "SELECT * FROM quotes ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Quote findRandomQuote();
}
