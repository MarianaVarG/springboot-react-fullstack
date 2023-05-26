package com.solera.backendcursojava.repositories;

import com.solera.backendcursojava.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail (String email);
}
