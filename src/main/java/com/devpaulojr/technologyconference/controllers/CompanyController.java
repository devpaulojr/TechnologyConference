package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.CompanyDto;
import com.devpaulojr.technologyconference.controllers.dtos.response.CompanyResponseDto;
import com.devpaulojr.technologyconference.controllers.mappers.CompanyMapper;
import com.devpaulojr.technologyconference.controllers.mappers.response.CompanyResponseMapper;
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
    private final CompanyMapper companyMapper;
    private final CompanyResponseMapper companyResponseMapper;


    @GetMapping(value = "/all")
    public ResponseEntity<List<CompanyDto>> findAllDetails(){

        List<Company> companies = service.findAll();
        List<CompanyDto> dtos = companies.stream().map(companyMapper::toDto).toList();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/all/{id}")
    public ResponseEntity<CompanyDto> findByIdDetails(@PathVariable UUID id){

        var company = service.findById(id);

        var dtos = companyMapper.toDto(company);

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> findAll(){

        List<Company> companies = service.findAll();

        List<CompanyResponseDto> dtos = companies
                .stream()
                .map(companyResponseMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyResponseDto> findById(@PathVariable UUID id){

        var company = service.findById(id);

        var dto = companyResponseMapper.toEntity(company);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<CompanyResponseDto>> specification(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cnpj", required = false) String cnpj,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "neighborhood", required = false) String neighborhood,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "state", required = false) String state){

        var specification = service.specification(name, cnpj, address, neighborhood, city, state);

        List<CompanyResponseDto> dtos = specification
                .stream()
                .map(companyResponseMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid CompanyDto companyDto){

        var company = companyMapper.toEntity(companyDto);

        company = service.insert(company);

        URI uri = uriGenerator(company.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CompanyDto companyDto){

        var company = companyMapper.toEntity(companyDto);

        company = service.update(id, company);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
