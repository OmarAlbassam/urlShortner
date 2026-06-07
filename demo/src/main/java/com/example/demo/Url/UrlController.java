package com.example.demo.Url;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<URL> shortenUrl(@RequestBody ShortenRequest request) {
        URL newUrl = urlService.shortenUrl(request.url(), request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newUrl);
    }
    
    @GetMapping("/urls")
    public ResponseEntity<List<URL>> getAllUrls(@RequestParam Long userId) {
        List<URL> urls = urlService.getAllUrls(userId);
        return ResponseEntity.status(HttpStatus.OK).body(urls);
    }

    
    @DeleteMapping("/urls")
    public ResponseEntity<Void> deleteById(@RequestParam Long urlId) {
        urlService.deleteById(urlId);
        return ResponseEntity.noContent().build();
    }
}
