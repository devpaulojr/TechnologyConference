package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.controllers.exceptions.ResourceNotFoundException;
import com.devpaulojr.technologyconference.model.Company;
import com.devpaulojr.technologyconference.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.devpaulojr.technologyconference.repositories.specs.CompanySpecification.*;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository repository;


    public List<Company> findAll(){
        return repository.findAll();
    }

    public Company findById(UUID id){

        Optional<Company> company = repository.findById(id);

        if(company.isPresent()){
            return company.get();
        }
        throw new ResourceNotFoundException("Não foi possível achar o id: " + id);
    }

    public Company insert(Company company){

        company.setState(company.getState().toUpperCase());

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

    public void deleteById(UUID id){

        Optional<Company> company = Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível achar o id: " + id)));

        repository.deleteById(id);
    }

    public List<Company> specification(String name,
                                       String cnpj,
                                       String address,
                                       String neighborhood,
                                       String city,
                                       String state){

        Specification<Company> specs = Specification
                .where(((root, query, cb) -> cb.conjunction()));


        if(name != null){
            specs = specs.and(likeName(name));
        }

        if(cnpj != null){
            specs = specs.and(equalCnpj(cnpj));
        }

        if(address != null){
            specs = specs.and(likeAddress(address));
        }

        if(neighborhood != null){
            specs = specs.and(likeNeighborhood(neighborhood));
        }

        if(city != null){
            specs = specs.and(likeCity(city));
        }

        if(state != null){
            specs = specs.and(equalState(state));
        }
        return repository.findAll(specs);
    }
}
