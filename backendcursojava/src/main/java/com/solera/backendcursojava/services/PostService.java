package com.solera.backendcursojava.services;

import com.solera.backendcursojava.entities.ExposureEntity;
import com.solera.backendcursojava.entities.PostEntity;
import com.solera.backendcursojava.entities.UserEntity;
import com.solera.backendcursojava.exceptions.PermissionsException;
import com.solera.backendcursojava.repositories.ExposureRepository;
import com.solera.backendcursojava.repositories.PostRepository;
import com.solera.backendcursojava.repositories.UserRepository;
import com.solera.backendcursojava.share.dto.PostCreationDto;
import com.solera.backendcursojava.share.dto.PostDto;
import com.solera.backendcursojava.utils.Exposures;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostService implements PostServiceInterface {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExposureRepository exposureRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public PostDto createPost(PostCreationDto post) {
        UserEntity userEntity = userRepository.findByEmail(post.getUserEmail());
        ExposureEntity exposureEntity = exposureRepository.findById(post.getExposureId());

        PostEntity postEntity = new PostEntity();
        postEntity.setUser(userEntity);
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setPostId(UUID.randomUUID().toString());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (post.getExpirationTime() * 60000L)));

        PostEntity createdPost = postRepository.save(postEntity);
        return mapper.map(createdPost, PostDto.class);
    }

    @Override
    public List<PostDto> getLastPost() {
        List<PostEntity> postEntities = postRepository.getLastPublicPosts(Exposures.PUBLIC_EXPOSURE, new Date(System.currentTimeMillis()));

        List<PostDto> postsDto = new ArrayList<>();

        for (PostEntity post: postEntities) {
            postsDto.add(mapper.map(post, PostDto.class));
        }
        return postsDto;
    }

    /***
     * Get post by ID
     * @param id post ID
     * @return
     */
    @Override
    public PostDto getPost(String postId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        // Convert to PostDTO and return it
        return mapper.map(postEntity, PostDto.class);
    }

    @Override
    public void deletePost(String postId, long userId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if (postEntity.getUser().getId() != userId)
            throw new PermissionsException("Not allowed to perform this action");

        postRepository.delete(postEntity);
    }

    @Override
    public PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if (postEntity.getUser().getId() != userId)
            throw new PermissionsException("Not allowed to perform this action");

        ExposureEntity exposureEntity = exposureRepository.findById(postUpdateDto.getExposureId());

        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(postUpdateDto.getTitle());
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (postUpdateDto.getExpirationTime() * 60000L)));

        // Save post
        PostEntity updatedPost = postRepository.save(postEntity);

        // Mapping and return
        return mapper.map(updatedPost, PostDto.class);
    }
}
