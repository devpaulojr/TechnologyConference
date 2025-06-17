package com.devpaulojr.technologyconference.security.service;

import com.devpaulojr.technologyconference.security.UserAccount;
import com.devpaulojr.technologyconference.security.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository repository;
    private final BCryptPasswordEncoder encoder;


    public List<UserAccount> findAll(){
        return repository.findAll();
    }

    public UserAccount findByLogin(String login){
        return repository.findByLogin(login);
    }

    public UserAccount insert(UserAccount userAccount){

        var password = userAccount.getPassword();

        userAccount.setLogin(userAccount.getLogin().toUpperCase());
        userAccount.setPassword(encoder.encode(password));
        userAccount.setRoles(userAccount.getRoles().stream().map(String::toUpperCase).toList());

        return repository.save(userAccount);
    }
}
