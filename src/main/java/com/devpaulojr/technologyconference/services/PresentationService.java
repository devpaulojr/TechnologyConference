package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.model.Presentation;
import com.devpaulojr.technologyconference.repositories.PresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PresentationService {

    private final PresentationRepository repository;


    public List<Presentation> findAll(){
        return repository.findAll();
    }

    public Presentation insert(Presentation presentation){
        return repository.save(presentation);
    }
}
