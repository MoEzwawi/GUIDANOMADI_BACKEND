package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Comment;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Post;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotYourPostException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.comments.NewCommentDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts.NewPostDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts.UpdatePostDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.CommentsRepository;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommunityService {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private CommentsRepository commentsRepository;
    public Page<Post> getPosts(int pageNumber, int size, String directionString){
        Sort.Direction direction = Objects.equals(directionString, "DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction,"createdAt"));
        return this.postsRepository.findAll(pageable);
    }
    public Post getPostById(long id){
        return this.postsRepository.findById(id).orElseThrow(() ->new NotFoundException(id));
    }
    public Post createNewPost(NewPostDTO body, User currentUser){
        Post newPost = new Post(body.text(), body.imageUrl(), currentUser);
        return this.postsRepository.save(newPost);
    }
    public boolean canIEditThisPost(User currentUser, Post found){
        return Objects.equals(found.getPostedBy().getId(), currentUser.getId());
    }
    public Post updatePostById(User currentUser, UpdatePostDTO body, long postId){
        Post found = this.getPostById(postId);
        if(!canIEditThisPost(currentUser, found)){
            throw new NotYourPostException();
        }
        found.setImageUrl(body.imageUrl());
        found.setText(body.text());
        return this.postsRepository.save(found);
    }
    public void deleteCommentsByPost(Post post){
        List<Comment> comments = this.commentsRepository.getAllCommentsByPost(post);
        this.commentsRepository.deleteAll(comments);
    }
    public void deletePostById(User currentUser, long postId){
        Post found = this.getPostById(postId);
        if(!canIEditThisPost(currentUser, found)){
            throw new NotYourPostException();
        }
        this.deleteCommentsByPost(found);
        this.postsRepository.delete(found);
    }
    public Page<Comment> getCommentsByPost(Post post, int pageNumber, int size){
        Pageable pageable = PageRequest.of(pageNumber, size);
        return this.commentsRepository.findByPost(post, pageable);
    }
    public Comment getCommentById(long id){
        return this.commentsRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
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