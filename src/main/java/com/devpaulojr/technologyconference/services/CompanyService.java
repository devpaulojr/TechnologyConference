package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.controllers.exceptions.ResourceNotFoundException;
import com.devpaulojr.technologyconference.model.Company;
import com.devpaulojr.technologyconference.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Company update(UUID id, Company company){

        Optional<Company> entity = repository.findById(id);

        if(entity.isPresent()){
            updated(entity, company);
            return repository.save(entity.get());
        }
        throw new ResourceNotFoundException("Não foi possível achar o id: " + id);

    }

    private void updated(Optional<Company> entity, Company company) {

        if(entity.isPresent()){
            Optional.ofNullable(company.getName()).ifPresent(entity.get()::setName);
            Optional.ofNullable(company.getCnpj()).ifPresent(entity.get()::setCnpj);
            Optional.ofNullable(company.getAddress()).ifPresent(entity.get()::setAddress);
            Optional.ofNullable(company.getNeighborhood()).ifPresent(entity.get()::setNeighborhood);
            Optional.ofNullable(company.getCity()).ifPresent(entity.get()::setCity);
            Optional.ofNullable(company.getState()).ifPresent(entity.get()::setState);
        }

    }
}
