package com.devpaulojr.technologyconference.security.repository;

import com.devpaulojr.technologyconference.security.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    UserAccount findByLogin(String login);
}
