package com.example.demo.Url;

import com.example.demo.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "URL")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    private Long urlId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String url;

    @Column(name = "shortened_url", nullable = false, unique = true)
    private String shortenedUrl;

    public URL(Long userId, String url, String shortCode) {
        User user = new User();
        user.setUserId(userId);
        this.user = user;
        this.url = url;
        this.shortenedUrl = shortCode;
    }
}
