package com.devpaulojr.technologyconference.repositories;

import com.devpaulojr.technologyconference.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
