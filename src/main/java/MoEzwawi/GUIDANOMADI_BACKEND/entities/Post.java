package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    @Column(length = 1000)
    private String text;
    @Setter
    @Column(name = "img_url")
    private String imageUrl;
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "posted_by_id")
    private User postedBy;

    public Post(String text, String imageUrl, User postedBy) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.postedBy = postedBy;
    }
}
