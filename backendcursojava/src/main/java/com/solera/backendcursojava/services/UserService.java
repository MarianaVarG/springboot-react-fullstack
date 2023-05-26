package com.solera.backendcursojava.services;

import com.solera.backendcursojava.entities.PostEntity;
import com.solera.backendcursojava.entities.UserEntity;
import com.solera.backendcursojava.exceptions.EmailExistsException;
import com.solera.backendcursojava.repositories.PostRepository;
import com.solera.backendcursojava.repositories.UserRepository;
import com.solera.backendcursojava.share.dto.PostDto;
import com.solera.backendcursojava.share.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto user) {
        /*
        * Validation: not create a user with same email
        */
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailExistsException("e-mail already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        //Automatic and random id
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());

        // When we use the "save" method of the repository, a user of type
        //  UserEntity returns us and that will be the one that the DB returns us
        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userToReturn);

        return userToReturn;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(userEntity, userToReturn);

        return userToReturn;
    }

    @Override
    public List<PostDto> getUserPosts(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        List<PostEntity> postsEntity = postRepository.getByUserIdOrderByCreatedAtDesc(userEntity.getId());

        List<PostDto> postsDto = new ArrayList<>();

        for (PostEntity post: postsEntity) {
            PostDto postDto = mapper.map(post, PostDto.class);
            postsDto.add(postDto);
        }

        return postsDto;
    }
}
