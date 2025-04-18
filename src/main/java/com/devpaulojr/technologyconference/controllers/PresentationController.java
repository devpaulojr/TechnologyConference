package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.PresentationDto;
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
    private final PresentationMapper mapper;


    @GetMapping
    public ResponseEntity<List<PresentationDto>> findAll(){

        List<Presentation> presentations = service.findAll();

        List<PresentationDto> dtos = presentations
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid PresentationDto presentationDto){

        var presentation = mapper.toEntity(presentationDto);

        presentation = service.insert(presentation);

        URI uri = uriGenerator(presentation.getId());

        return ResponseEntity.created(uri).build();
    }
}
