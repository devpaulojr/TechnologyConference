package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.RoomDto;
import com.devpaulojr.technologyconference.controllers.mappers.RoomMapper;
import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.model.Room;
import com.devpaulojr.technologyconference.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/rooms")
public class RoomController implements UriGenerator {

    private final RoomService service;
    private final RoomMapper mapper;


    @GetMapping
    public ResponseEntity<List<RoomDto>> findAll(){

        List<Room> rooms = service.findAll();

        List<RoomDto> dtos = rooms
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid RoomDto roomDto){

        Room room = mapper.toEntity(roomDto);

        room = service.insert(room);

        URI uri = uriGenerator(room.getId());

        return ResponseEntity.created(uri).build();
    }
}
