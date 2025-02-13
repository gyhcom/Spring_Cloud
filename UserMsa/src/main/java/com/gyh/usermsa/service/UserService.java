package com.gyh.usermsa.service;

import com.gyh.usermsa.dto.UserDto;
import com.gyh.usermsa.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

//    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String email);
}
