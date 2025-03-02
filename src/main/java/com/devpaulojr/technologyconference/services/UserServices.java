package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServices {

    private final UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }
}
