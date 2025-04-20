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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<PresentationCreatedDto>> findAll(){

        List<Presentation> presentations = service.findAll();

        List<PresentationCreatedDto> dtos = presentations
                .stream()
                .map(presentationCreatedMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid PresentationDto presentationDto){

        var presentation = presentationMapper.toEntity(presentationDto);

        presentation = service.insert(presentation);

        URI uri = uriGenerator(presentation.getId());

        return ResponseEntity.created(uri).build();
    }
}
