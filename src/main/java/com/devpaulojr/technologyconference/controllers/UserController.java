package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.UserDto;
import com.devpaulojr.technologyconference.controllers.mappers.UserMapper;
import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(){

        List<User> userList = service.findAll();

        List<UserDto> userDtos = userList
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok().body(userDtos);
    }
}
