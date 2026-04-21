package com.postgres.android_proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.postgres.android_proyect.models.android_tweet;
import java.util.List;

@Repository
public interface AndroidRepository extends JpaRepository<android_tweet, Long> {
    List<android_tweet> findByUserId(Long userId);
}