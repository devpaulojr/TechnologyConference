package com.devpaulojr.technologyconference.security.controller;

import com.devpaulojr.technologyconference.controllers.util.UriGenerator;
import com.devpaulojr.technologyconference.security.UserAccount;
import com.devpaulojr.technologyconference.security.dto.UserAccountDto;
import com.devpaulojr.technologyconference.security.mapper.UserAccountMapper;
import com.devpaulojr.technologyconference.security.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/userAccounts")
@RequiredArgsConstructor
public class UserAccountController implements UriGenerator {

    private final UserAccountService service;
    private final UserAccountMapper mapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'CEO', 'GERENTE')")
    @GetMapping
    public ResponseEntity<List<UserAccountDto>> findAll(){

        List<UserAccount> userAccounts = service.findAll();
        List<UserAccountDto> dtos = userAccounts.stream().map(mapper::toDto).toList();

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody @Valid UserAccountDto userAccountDto){

        var userAccount = mapper.toEntity(userAccountDto);

        userAccount = service.insert(userAccount);

        URI uri = uriGenerator(userAccount.getId());

        return ResponseEntity.created(uri).build();
    }
}
