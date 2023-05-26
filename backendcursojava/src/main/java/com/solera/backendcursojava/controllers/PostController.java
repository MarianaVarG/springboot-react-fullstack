package com.solera.backendcursojava.controllers;

import com.solera.backendcursojava.exceptions.PermissionsException;
import com.solera.backendcursojava.models.reponses.OperationStatusModel;
import com.solera.backendcursojava.models.reponses.PostRest;
import com.solera.backendcursojava.models.requests.PostCreateRequestModel;
import com.solera.backendcursojava.services.PostServiceInterface;
import com.solera.backendcursojava.services.UserServiceInterface;
import com.solera.backendcursojava.share.dto.PostCreationDto;
import com.solera.backendcursojava.share.dto.PostDto;
import com.solera.backendcursojava.share.dto.UserDto;
import com.solera.backendcursojava.utils.Exposures;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that handles HTTP requests related to posts.
 * Provides endpoints for creating, retrieving, updating, and deleting posts.
 */
@RestController
@RequestMapping("/posts")
public class PostController {
    static final Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    PostServiceInterface postService;

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    /**
     * Handles the HTTP POST request to create a new post.
     *
     * @param createRequestModel The request body containing the data to create a new post.
     * @return The newly created post represented as a {@link PostRest} object.
     */
    @PostMapping
    public PostRest createPost(@Valid @RequestBody PostCreateRequestModel createRequestModel) {
        // Logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        // Mapping
        PostCreationDto postCreationDto = mapper.map(createRequestModel, PostCreationDto.class);
        postCreationDto.setUserEmail(email);
        PostDto postDto = postService.createPost(postCreationDto);

        return mapper.map(postDto, PostRest.class);
    }

    /**
     * Retrieves the last 20 public posts.
     *
     * @return A list of the last 20 public posts represented as a list of {@link PostRest} objects.
     */
    @GetMapping(path = "/last")
    public List<PostRest> lastPosts() {
        List<PostDto> postDtoList = postService.getLastPost();
        // Convert PostDto list to PostRest list
        List<PostRest> postsRest = new ArrayList<>();
        for (PostDto post: postDtoList) {
            postsRest.add(mapper.map(post, PostRest.class));
        }

        return postsRest;
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param id The ID of the post to retrieve.
     * @return The post with the specified ID represented as a {@link PostRest} object.
     * @throws PermissionsException If the post is private or expired and the authenticated user does not have permission to access it.
     */
    @GetMapping(path = "/{id}")
    public PostRest getPost(@PathVariable String id){
        logger.info("PostController: getPost method");
        PostDto postDto = postService.getPost(id);
        PostRest postRest = mapper.map(postDto, PostRest.class);

        //Validate if post is private or/and expired
        if (postRest.getExposure().getId() == Exposures.PRIVATE_EXPOSURE || postRest.isExpired()) {
            // Logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDto user =  userService.getUser(authentication.getPrincipal().toString());

            if (user.getId() != postDto.getUser().getId()) {
                throw new PermissionsException("Not allowed to perform this action");
            }
        }
        return postRest;
    }

    /**
     * Deletes a post by its ID.
     *
     * @param id The ID of the post to delete.
     * @return The operation status model indicating the success of the delete operation.
     */
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deletePost(@PathVariable String id) {
        logger.info("PostController: deletePost method");
        // Logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user =  userService.getUser(authentication.getPrincipal().toString());

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setName("DELETE");

        postService.deletePost(id, user.getId());
        operationStatusModel.setResult("SUCCESS");

        return operationStatusModel;
    }

    /**
     * Updates a post by its ID.
     *
     * @param postCreateRequestModel The request body containing the updated data for the post.
     * @param id                     The ID of the post to update.
     * @return The updated post represented as a {@link PostRest} object.
     */
    @PutMapping(path = "/{id}")
    public PostRest updatePost(@Valid @RequestBody PostCreateRequestModel postCreateRequestModel, @PathVariable String id) {
        logger.info("PostController: updatePost method");
        // Logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user =  userService.getUser(authentication.getPrincipal().toString());

        // Map and Update post
        PostCreationDto postUpdateDto = mapper.map(postCreateRequestModel, PostCreationDto.class);
        PostDto postDto = postService.updatePost(id, user.getId(), postUpdateDto);

        // Map post to return
        return mapper.map(postDto, PostRest.class);
    }
}
