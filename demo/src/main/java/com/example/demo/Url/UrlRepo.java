package com.example.demo.Url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepo extends JpaRepository<URL, Long> {
    boolean existsByShortenedUrl(String shortenedUrl);
    List<URL> findByUser_UserId(Long userId);

}


