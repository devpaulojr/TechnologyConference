package com.devpaulojr.technologyconference.security.service;

import com.devpaulojr.technologyconference.security.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserAccountService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserAccount userAccount = service.findByLogin(login);

        if (userAccount == null) {
            throw new UsernameNotFoundException("Usuário inválido: " + login);
        }

        return User
                .builder()
                .username(userAccount.getLogin())
                .password(userAccount.getPassword())
                .roles(userAccount.getRoles().toArray(new String[userAccount.getRoles().size()]))
                .build();
    }
}
