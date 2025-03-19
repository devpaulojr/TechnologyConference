package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.CompanyDto;
import com.devpaulojr.technologyconference.controllers.mappers.CompanyMapper;
import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.model.Company;
import com.devpaulojr.technologyconference.services.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/companies")
public class CompanyController implements UriGenerator {

    private final CompanyService service;
    private final CompanyMapper mapper;


    @GetMapping
    public ResponseEntity<List<CompanyDto>> insert(){

        List<Company> companies = service.findAll();

        List<CompanyDto> companyDtos = companies
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok().body(companyDtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid CompanyDto companyDto){

        Company company = mapper.toEntity(companyDto);

        company = service.insert(company);

        URI uri = uriGenerator(company.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CompanyDto companyDto){

        Company company = mapper.toEntity(companyDto);

        company = service.update(id, company);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
