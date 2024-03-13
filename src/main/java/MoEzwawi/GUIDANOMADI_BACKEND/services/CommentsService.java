package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Comment;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Post;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.comments.NewCommentDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.CommentsRepository;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private PostsRepository postsRepository;
    public Page<Comment> getCommentsByPost(Post post, int pageNumber, int size){
        Pageable pageable = PageRequest.of(pageNumber, size);
        return this.commentsRepository.findByPost(post, pageable);
    }
    public Comment getCommentById(long id){
        return this.commentsRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public void deleteCommentsByPost(Post post){
        List<Comment> comments = this.commentsRepository.getAllCommentsByPost(post);
        this.commentsRepository.deleteAll(comments);
    }
    public void deleteCommentById(long id){
        Comment found = this.getCommentById(id);
        this.commentsRepository.delete(found);
    }
    public Comment addNewComment(NewCommentDTO body, User currentUser){
        Post found = this.postsRepository.findById(body.postId()).orElseThrow(() ->new NotFoundException(body.postId()));
        Comment newComment = new Comment(body.text(), found, currentUser);
        return this.commentsRepository.save(newComment);
    }
}