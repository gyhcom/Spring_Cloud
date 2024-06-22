package com.gyh.usermsa.jpa.repository;

import com.gyh.usermsa.jpa.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(String userId);
}
