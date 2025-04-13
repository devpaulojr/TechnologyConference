package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.RoomDto;
import com.devpaulojr.technologyconference.controllers.dtos.UserDto;
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
import java.util.UUID;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomDto> findById(@PathVariable UUID id){

        var room = service.findById(id);

        var dto = mapper.toDto(room);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid RoomDto roomDto){

        Room room = mapper.toEntity(roomDto);

        room = service.insert(room);

        URI uri = uriGenerator(room.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody RoomDto roomDto){

        var user = mapper.toEntity(roomDto);

        service.update(user, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){

        service.deleteAll();

        return ResponseEntity.noContent().build();
    }
}
