package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Post;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts.NewPostDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts.UpdatePostDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private CommentsService commentsService;
    public Page<Post> getPosts(int pageNumber, int size, Sort.Direction direction){
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction,"createdAt"));
        return this.postsRepository.findAll(pageable);
    }
    public Post getPostById(long id){
        return this.postsRepository.findById(id).orElseThrow(() ->new NotFoundException(id));
    }
    public Post updatePostById(UpdatePostDTO body){
        Post found = this.getPostById(body.postId());
        found.setImageUrl(body.imageUrl());
        found.setText(body.text());
        return this.postsRepository.save(found);
    }
    public Post createNewPost(NewPostDTO body, User currentUser){
        Post newPost = new Post(body.text(), body.imageUrl(), currentUser);
        return this.postsRepository.save(newPost);
    }
    public void deletePostById(long postId){
        Post found = this.getPostById(postId);
        this.commentsService.deleteCommentsByPost(found);
        this.postsRepository.delete(found);
    }
}
