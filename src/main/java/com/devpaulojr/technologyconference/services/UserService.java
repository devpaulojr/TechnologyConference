package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User insert(User user){

        if(!Objects.equals(user.getPassword(), user.getConfirmPassword())){
            throw new IllegalArgumentException("senha errada.");
        }

        return repository.save(user);
    }

}
