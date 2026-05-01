package com.postgres.android_proyect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.postgres.android_proyect.models.AndroidVersion;
import java.util.List;

@Repository
public interface AndroidRepository extends JpaRepository<AndroidVersion, Long> {
    List<AndroidVersion> findByUserId(Long userId);
}