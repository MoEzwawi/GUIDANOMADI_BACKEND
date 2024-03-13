package MoEzwawi.GUIDANOMADI_BACKEND.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    @Column(length = 500)
    private String text;
    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "commented_by_id")
    private User commentedBy;

    public Comment(String text, Post post, User commentedBy) {
        this.text = text;
        this.post = post;
        this.commentedBy = commentedBy;
    }
}
