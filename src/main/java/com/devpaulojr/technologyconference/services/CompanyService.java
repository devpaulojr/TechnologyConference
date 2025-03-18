package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.model.Company;
import com.devpaulojr.technologyconference.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository repository;


    public List<Company> findAll(){
        return repository.findAll();
    }

    public Company insert(Company company){
        return repository.save(company);
    }
}
