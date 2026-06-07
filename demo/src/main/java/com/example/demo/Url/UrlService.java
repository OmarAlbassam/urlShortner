package com.example.demo.Url;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UrlService {
    private final UrlRepo urlRepo;
    UrlService(UrlRepo urlRepo) {
        this.urlRepo = urlRepo;
    }

    public URL shortenUrl(String url, Long userId) {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (urlRepo.existsByShortenedUrl(shortCode));
        URL newUrl = new URL(userId, url, shortCode);
        urlRepo.save(newUrl);
        return newUrl;
    }

    public List<URL> getAllUrls(Long userId) {
        return urlRepo.findByUser_UserId(userId);
    }

    public URL deleteById(Long urlId) {
        URL url = urlRepo.findById(urlId).orElseThrow();
        urlRepo.deleteById(urlId);
        return url;
    }

    private String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            shortCode.append(chars.charAt(random.nextInt(chars.length())));
        }

        return shortCode.toString();
    }

}
