package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.model.Presentation;
import com.devpaulojr.technologyconference.repositories.PresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.devpaulojr.technologyconference.services.validations.PresentationValidation.*;

@RequiredArgsConstructor
@Service
public class PresentationService {

    private final PresentationRepository repository;


    public List<Presentation> findAll(){
        return repository.findAll();
    }

    public Presentation insert(Presentation presentation){

        List<Presentation> presentationList = repository.findAll();
        List<String> presentationName = repository.findAll().stream().map(Presentation::getName).toList();

        for(String value : presentationName){

            if(Objects.equals(value, presentation.getName())){
                throw new BadRequestException("Valor do nome invalido, tente outro nome. " + presentation.getName());
            }
        }

        // validação da classe presentation
        presentationValidation(presentation, presentationList);

        return repository.save(presentation);
    }
}
