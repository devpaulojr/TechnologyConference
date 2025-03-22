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
    public ResponseEntity<List<CompanyDto>> findAll(){

        List<Company> companies = service.findAll();

        List<CompanyDto> companyDtos = companies
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok().body(companyDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable UUID id){

        var company = service.findById(id);

        CompanyDto dto = mapper.toDto(company);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<CompanyDto>> specification(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "neighborhood", required = false) String neighborhood,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state){

        var specification = service.specification(name, cnpj, address, neighborhood, city, state);

        List<CompanyDto> dtos = specification
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid CompanyDto companyDto){

        var company = mapper.toEntity(companyDto);

        company = service.insert(company);

        URI uri = uriGenerator(company.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CompanyDto companyDto){

        var company = mapper.toEntity(companyDto);

        company = service.update(id, company);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
