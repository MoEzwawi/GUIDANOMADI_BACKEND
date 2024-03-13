package MoEzwawi.GUIDANOMADI_BACKEND.repositories;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Comment;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post = :post")
    Page<Comment> findByPost(@Param("post") Post post, Pageable pageable);
    @Query("SELECT c FROM Comment c WHERE c.post = :post")
    List<Comment> getAllCommentsByPost(@Param("post") Post post);
}
