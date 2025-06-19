package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.response.PresentationResponseDto;
import com.devpaulojr.technologyconference.controllers.dtos.PresentationDto;
import com.devpaulojr.technologyconference.controllers.mappers.response.PresentationResponseMapper;
import com.devpaulojr.technologyconference.controllers.mappers.PresentationMapper;
import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.model.Presentation;
import com.devpaulojr.technologyconference.services.PresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/presentations")
public class PresentationController implements UriGenerator {

    private final PresentationService service;
    private final PresentationMapper presentationMapper;
    private final PresentationResponseMapper presentationCreatedMapper;


    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<PresentationDto>> findAllDetails(){

        List<Presentation> presentations = service.findAll();

        List<PresentationDto> dtos = presentations
                .stream()
                .map(presentationMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE')")
    @GetMapping(value = "/all/{id}")
    public ResponseEntity<PresentationDto> findByIdDetails(@PathVariable UUID id){

        var presentation = service.findById(id);

        var dto = presentationMapper.toDto(presentation);

        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<PresentationResponseDto>> findAll(){

        List<Presentation> presentations = service.findAll();

        List<PresentationResponseDto> dtos = presentations
                .stream()
                .map(presentationCreatedMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PresentationResponseDto> findById(@PathVariable UUID id){

        var presentation = service.findById(id);

        var dto = presentationCreatedMapper.toDto(presentation);

        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @GetMapping(value = "/filter")
    public ResponseEntity<List<PresentationResponseDto>> specs(@RequestParam(required = false, value = "name")
                                                              String name,
                                                               @RequestParam(required = false, value = "startTime")
                                                              Integer startTime) {

        List<Presentation> presentations = service.specification(name, startTime);

        List<PresentationResponseDto> dtos = presentations
                .stream()
                .map(presentationCreatedMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid PresentationDto presentationDto){

        var presentation = presentationMapper.toEntity(presentationDto);

        presentation = service.insert(presentation);

        URI uri = uriGenerator(presentation.getId());

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody PresentationResponseDto presentationResponseDto){

        var presentation = presentationCreatedMapper.toEntity(presentationResponseDto);

        service.update(presentation, id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
