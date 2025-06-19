package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.RoomDto;
import com.devpaulojr.technologyconference.controllers.dtos.response.RoomResponseDto;
import com.devpaulojr.technologyconference.controllers.mappers.RoomMapper;
import com.devpaulojr.technologyconference.controllers.mappers.response.RoomResponseMapper;
import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.model.Room;
import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import com.devpaulojr.technologyconference.model.enums.RoomType;
import com.devpaulojr.technologyconference.services.RoomService;
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
@RequestMapping(value = "/rooms")
public class RoomController implements UriGenerator {

    private final RoomService service;
    private final RoomMapper roomMapper;
    private final RoomResponseMapper roomResponseMapper;


    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<RoomDto>> findAllDetails(){

        List<Room> rooms = service.findAll();

        List<RoomDto> dtos = rooms.stream().map(roomMapper::toDto).toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE')")
    @GetMapping(value = "/all/{id}")
    public ResponseEntity<RoomDto> findByIdDetails(@PathVariable UUID id){

        var room = service.findById(id);

        var dto = roomMapper.toDto(room);

        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> findAll(){

        List<Room> rooms = service.findAll();

        List<RoomResponseDto> dtos = rooms
                .stream()
                .map(roomResponseMapper::toDto)
                .toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomResponseDto> findById(@PathVariable UUID id){

        var room = service.findById(id);

        var dto = roomResponseMapper.toDto(room);

        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @GetMapping(value = "/filter")
    public ResponseEntity<List<RoomResponseDto>> specs(@RequestParam(required = false, value = "numberRooms")
                                               Integer numberRooms,
                                               @RequestParam(required = false, value = "roomStatus")
                                               RoomStatus roomStatus,
                                               @RequestParam(required = false, value = "roomType")
                                               RoomType roomType) {

        List<Room> rooms = service.specification(numberRooms, roomStatus, roomType);

        List<RoomResponseDto> dtos = rooms
                .stream()
                .map(roomResponseMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('PARTICIPANT', 'ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid RoomDto roomDto){

        Room room = roomMapper.toEntity(roomDto);

        room = service.insert(room);

        URI uri = uriGenerator(room.getId());

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody RoomDto roomDto){

        var user = roomMapper.toEntity(roomDto);

        service.update(user, id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE', 'EMPLOYEE')")
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){

        service.deleteAll();

        return ResponseEntity.noContent().build();
    }
}
