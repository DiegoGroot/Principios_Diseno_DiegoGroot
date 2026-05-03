package com.postgres.android_proyect.repository;

import com.postgres.android_proyect.models.Reaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReaccionRepository extends JpaRepository<Reaccion, Long> {
    List<Reaccion> findByTweetId(Long tweetId);
    long countByTweetId(Long tweetId);
}