package com.gobbledygook.gobbledygook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface WordSelectRepository extends JpaRepository<WordEntity, Integer> {
    @Query(value = "SELECT * FROM words ORDER BY RAND() LIMIT 1", nativeQuery = true)
    WordEntity findRandomWord();
}

