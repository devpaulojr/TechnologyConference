package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.response.UserDetailsDto;
import com.devpaulojr.technologyconference.controllers.dtos.response.UserResponseDto;
import com.devpaulojr.technologyconference.controllers.dtos.UserDto;
import com.devpaulojr.technologyconference.controllers.mappers.response.UserDetailsMapper;
import com.devpaulojr.technologyconference.controllers.mappers.response.UserResponseMapper;
import com.devpaulojr.technologyconference.controllers.mappers.UserMapper;
import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/users")
public class UserController implements UriGenerator {

    private final UserService service;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final UserDetailsMapper userDetailsMapper;


    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDetailsDto>> findAllDetails(){

        List<User> users = service.findAll();
        List<UserDetailsDto> dtos = users.stream().map(userDetailsMapper::toDto).toList();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/all/{id}")
    public ResponseEntity<UserDetailsDto> findByIdDetails(@PathVariable UUID id){

        var user = service.findById(id);
        var dtos = userDetailsMapper.toDto(user);

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){

        List<User> userList = service.findAll();

        List<UserResponseDto> userCreateDtos = userList.stream().map(userResponseMapper::toDto).toList();

        return ResponseEntity.ok().body(userCreateDtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id){

        var user = service.findById(id);

        UserResponseDto dto = userResponseMapper.toDto(user);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<UserResponseDto>> specs(
            @RequestParam(required = false, value = "firstName") String firstName,
            @RequestParam(required = false, value = "lastName") String lastName,
            @RequestParam(required = false, value = "email") String email,
            @RequestParam(required = false, value = "phoneNumber") String phoneNumber,
            @RequestParam(required = false, value = "vip") Boolean vip){

        var specification = service.specification(firstName, lastName, email, phoneNumber, vip);

        var dto = specification.stream().map(userResponseMapper::toDto).toList();

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid UserDto userDto) {

        var user = userMapper.toEntity(userDto);

        user = service.insert(user);

        URI uri = uriGenerator(user.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody UserDto userDto){

        var user = userMapper.toEntity(userDto);

        service.update(user, id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
