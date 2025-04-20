package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.controllers.exceptions.ResourceNotFoundException;
import com.devpaulojr.technologyconference.model.Presentation;
import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.repositories.PresentationRepository;
import com.devpaulojr.technologyconference.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.devpaulojr.technologyconference.services.validations.PresentationValidation.*;

@RequiredArgsConstructor
@Service
public class PresentationService {

    private final PresentationRepository presentationRepository;
    private final RoomRepository roomRepository;


    public List<Presentation> findAll(){
        return presentationRepository.findAll();
    }

    public Presentation findById(UUID id){
        return presentationRepository.findById(id)
                .orElseThrow( () ->  new ResourceNotFoundException("Não foi possível achar o id: " + id) );
    }

    public Presentation insert(Presentation presentation){

        var room = roomRepository.findById(presentation.getRoom().getId())
                .orElseThrow( () -> new BadRequestException("Sala não encontrada para cadastrar a conferência.") );

        var users = room.getCompany().getUsers();

        if(users.isEmpty()){
            throw new BadRequestException("Nenhum usuário encontrado para cadastrar a conferência.");
        }

        List<Presentation> presentationList = presentationRepository.findAll();
        List<String> presentationName = presentationRepository.findAll().stream().map(Presentation::getName).toList();

        for(String value : presentationName){
            if(Objects.equals(value, presentation.getName())){
                throw new BadRequestException("Valor do nome invalido, tente outro nome. " + presentation.getName());
            }
        }

        // validação da classe presentation
        presentationValidation(presentation, presentationList);

        return presentationRepository.save(presentation);
    }

    public void update(Presentation presentation, UUID id){

        Optional<Presentation> presentationFound = presentationRepository.findById(id);

        if(presentationFound.isPresent()){
            updated(presentationFound, presentation);
            presentationRepository.save(presentationFound.get());
            return;
        }
        throw new ResourceNotFoundException("Não foi possível achar o id: " + id);
    }

    private void updated(Optional<Presentation> presentationFound, Presentation presentation) {
        presentationFound.ifPresent(value -> Optional.ofNullable(presentation.getName()).ifPresent(value::setName));
    }

    public void deleteById(UUID id){

        Optional<Presentation> user = Optional.ofNullable(presentationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível achar o id: " + id)));

        presentationRepository.deleteById(id);
    }
}
