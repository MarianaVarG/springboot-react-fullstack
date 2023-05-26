package com.solera.backendcursojava.services;

import com.solera.backendcursojava.share.dto.PostCreationDto;
import com.solera.backendcursojava.share.dto.PostDto;

import java.util.List;

public interface PostServiceInterface {
    public PostDto createPost (PostCreationDto postCreationDto);

    List<PostDto> getLastPost();

    PostDto getPost(String id);

    void deletePost(String postId, long userId);

    PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto);
}
