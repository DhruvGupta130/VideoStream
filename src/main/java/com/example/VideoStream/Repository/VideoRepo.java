package com.example.VideoStream.Repository;

import com.example.VideoStream.Entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepo extends JpaRepository<VideoEntity, String> {
    Optional<VideoEntity> findByTitle(String title);
}
