package com.solera.backendcursojava.services;

import com.solera.backendcursojava.share.dto.PostDto;
import com.solera.backendcursojava.share.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    public UserDto createUser(UserDto user);
    // Create an interface method for the header
    public UserDto getUser(String email);

    List<PostDto> getUserPosts(String email);
}
