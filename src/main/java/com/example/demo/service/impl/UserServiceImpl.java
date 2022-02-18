package com.example.demo.service.impl;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String getUserById(Long id) {
        return userRepository.findById(id).map(UserEntity::getFio)
            .orElseThrow(() -> new ObjectNotFoundException(id, "User entity"));
    }

}
