package com.solera.backendcursojava.repositories;

import com.solera.backendcursojava.entities.ExposureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExposureRepository extends CrudRepository<ExposureEntity, Long> {
    ExposureEntity findById(long id);
}
