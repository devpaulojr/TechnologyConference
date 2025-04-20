package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.PresentationCreatedDto;
import com.devpaulojr.technologyconference.controllers.dtos.PresentationDto;
import com.devpaulojr.technologyconference.controllers.mappers.PresentationCreatedMapper;
import com.devpaulojr.technologyconference.controllers.mappers.PresentationMapper;
import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.model.Presentation;
import com.devpaulojr.technologyconference.services.PresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/presentations")
public class PresentationController implements UriGenerator {

    private final PresentationService service;
    private final PresentationMapper presentationMapper;
    private final PresentationCreatedMapper presentationCreatedMapper;


    @GetMapping(value = "/all")
    public ResponseEntity<List<PresentationDto>> findAllDetails(){

        List<Presentation> presentations = service.findAll();

        List<PresentationDto> dtos = presentations
                .stream()
                .map(presentationMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/all/{id}")
    public ResponseEntity<PresentationDto> findByIdDetails(@PathVariable UUID id){

        var presentation = service.findById(id);

        var dto = presentationMapper.toDto(presentation);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<List<PresentationCreatedDto>> findAll(){

        List<Presentation> presentations = service.findAll();

        List<PresentationCreatedDto> dtos = presentations
                .stream()
                .map(presentationCreatedMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PresentationCreatedDto> findById(@PathVariable UUID id){

        var presentation = service.findById(id);

        var dto = presentationCreatedMapper.toDto(presentation);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid PresentationDto presentationDto){

        var presentation = presentationMapper.toEntity(presentationDto);

        presentation = service.insert(presentation);

        URI uri = uriGenerator(presentation.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody PresentationCreatedDto presentationCreatedDto){

        var presentation = presentationCreatedMapper.toEntity(presentationCreatedDto);

        service.update(presentation, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
