package com.devpaulojr.technologyconference.repositories;

import com.devpaulojr.technologyconference.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

}
