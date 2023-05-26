package com.solera.backendcursojava.controllers;

import com.solera.backendcursojava.models.reponses.PostRest;
import com.solera.backendcursojava.models.reponses.UserRest;
import com.solera.backendcursojava.models.requests.UserDetailsRequestModel;
import com.solera.backendcursojava.services.UserServiceInterface;
import com.solera.backendcursojava.share.dto.PostDto;
import com.solera.backendcursojava.share.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that handles HTTP requests related to users.
 * Provides endpoints for retrieving user details, creating users, and retrieving user posts.
 */
@RestController
@RequestMapping("/users") // localhost:8080/users
public class UserController {
    static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    /**
     * Retrieves the details of the authenticated user.
     *
     * @return The details of the authenticated user represented as a {@link UserRest} object.
     */
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser() {
        LOGGER.info("UserController: getUser method");
        // When authentication is successful, the security context will be set.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get the email of the logged-in user.
        String email = authentication.getPrincipal().toString();
        // Get the UserDto based on the email.
        UserDto userDto = userService.getUser(email);
        // Convert the UserDto to a UserRest object.
        return mapper.map(userDto, UserRest.class);
    }

    /**
     * Creates a new user.
     *
     * @param userDetails The request body containing the details of the user to create.
     * @return The newly created user represented as a {@link UserRest} object.
     */
    @PostMapping
    public UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {
        /*  ALL FOR SECURITY */
        // 1. We receive the object in JSON and transform it into a Java object.
        UserRest userToReturn = new UserRest();
        // 2. Prepare the object to send it to the project logic.
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        // 3. Create the user.
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, userToReturn);

        return userToReturn;
    }

    /**
     * Retrieves the posts of the authenticated user.
     *
     * @return A list of the posts of the authenticated user represented as a list of {@link PostRest} objects.
     */
    @GetMapping(path = "/posts")
    public List<PostRest> getPosts(){
        // When authentication is successful, the security context will be set.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Get the email of the logged-in user.
        String email = authentication.getPrincipal().toString();

        List<PostDto> postDtos = userService.getUserPosts(email);
        // Convert the list of PostDto objects to a list of PostRest objects.
        List<PostRest> postsRest = new ArrayList<>();
        for (PostDto post: postDtos) {
            PostRest postRest = mapper.map(post, PostRest.class);
            postsRest.add(postRest);
        }

        return postsRest;
    }
}
