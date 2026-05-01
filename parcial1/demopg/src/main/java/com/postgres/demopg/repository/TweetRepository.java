package com.postgres.demopg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.postgres.demopg.models.Tweet;

/**
 * Repositorio para acceso a datos de tweets
 * Proporciona operaciones CRUD y custom queries sobre la entidad Tweet
 */
@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    // JpaRepository proporciona: save(), findAll(), findById(), delete(), etc.
}
