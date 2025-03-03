package com.devpaulojr.technologyconference.controllers;

import com.devpaulojr.technologyconference.controllers.dtos.UserCreatedDto;
import com.devpaulojr.technologyconference.controllers.dtos.UserDto;
import com.devpaulojr.technologyconference.controllers.mappers.UserCreatedMapper;
import com.devpaulojr.technologyconference.controllers.mappers.UserMapper;
import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserController implements UriGenerator {

    private final UserService service;
    private final UserMapper userMapper;
    private final UserCreatedMapper userCreatedMapper;

    @GetMapping
    public ResponseEntity<List<UserCreatedDto>> findAll(){

        List<User> userList = service.findAll();

        List<UserCreatedDto> userCreateDtos = userList.stream().map(userCreatedMapper::toDto).toList();

        return ResponseEntity.ok().body(userCreateDtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid UserDto userDto) {

        User user = userMapper.toEntity(userDto);

        user = service.insert(user);

        URI uri = uriGenerator(user.getId());

        return ResponseEntity.created(uri).build();
    }
}
