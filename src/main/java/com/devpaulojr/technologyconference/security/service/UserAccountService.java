package com.devpaulojr.technologyconference.security.service;

import com.devpaulojr.technologyconference.security.UserAccount;
import com.devpaulojr.technologyconference.security.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository repository;
    private final PasswordEncoder encoder;


    public List<UserAccount> findAll(){
        return repository.findAll();
    }

    public UserAccount findByLogin(String login){
        return repository.findByLogin(login);
    }

    public UserAccount insert(UserAccount userAccount){

        var password = userAccount.getPassword();

        userAccount.setPassword(encoder.encode(password));

        userAccount.setRoles(userAccount.getRoles().stream().map(String::toUpperCase).toList());

        return repository.save(userAccount);
    }
}
