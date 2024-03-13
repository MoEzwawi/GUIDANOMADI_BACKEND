package MoEzwawi.GUIDANOMADI_BACKEND.controllers;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Post;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.User;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts.NewPostDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.payloads.posts.UpdatePostDTO;
import MoEzwawi.GUIDANOMADI_BACKEND.services.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @GetMapping
    public Page<Post> getPosts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String direction
    ){
        return this.communityService.getPosts(pageNumber, size, direction);
    }
    @PostMapping
    public Post createNewPost(
            @RequestBody NewPostDTO body,
            @AuthenticationPrincipal User currentUser
    ){
        return this.communityService.createNewPost(body, currentUser);
    }
    @PutMapping("/{id}")
    public Post updatePost(
            @RequestBody UpdatePostDTO body,
            @PathVariable long id,
            @AuthenticationPrincipal User currentUser
            ){
        return this.communityService.updatePostById(currentUser, body, id);
    }
    @DeleteMapping("/{id}")
    public void deletePost(
            @PathVariable long id,
            @AuthenticationPrincipal User currentUser
    ){
        this.communityService.deletePostById(currentUser, id);
    }
}
