package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.model.Presentation;
import com.devpaulojr.technologyconference.repositories.PresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PresentationService {

    private final PresentationRepository repository;


    public List<Presentation> findAll(){
        return repository.findAll();
    }

    public Presentation insert(Presentation presentation){

        List<Presentation> presentations = repository.findAll();
        List<String> names = presentations.stream().map(Presentation::getName).toList();

        for(String value : names){

            if(Objects.equals(value, presentation.getName())){
                throw new BadRequestException("Valor do nome invalido, tente outro nome. " + presentation.getName());
            }
        }

        return repository.save(presentation);
    }
}
