package com.gyh.usermsa.service;

import com.gyh.usermsa.dto.UserDto;
import com.gyh.usermsa.jpa.UserEntity;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
