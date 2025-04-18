package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.controllers.exceptions.ResourceNotFoundException;
import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.devpaulojr.technologyconference.repositories.specs.UserSpecification.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;


    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(UUID id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível achar o id: " + id));
    }

    public User insert(User user){

        if(!Objects.equals(user.getPassword(), user.getConfirmPassword())){
            throw new BadRequestException("Usuário ou senha inválidos. Verifique e tente novamente.");
        }
        return repository.save(user);
    }

    public void update(User user, UUID id){

        Optional<User> userFound = repository.findById(id);

        if(userFound.isPresent()){
            updated(userFound, user);
            repository.save(userFound.get());
            return;
        }
        throw new ResourceNotFoundException("Não foi possível achar o id: " + id);
    }

    private void updated(Optional<User> userFound , User user) {

        if(userFound.isPresent()){
            Optional.ofNullable(user.getFirstName()).ifPresent(userFound.get()::setFirstName);
            Optional.ofNullable(user.getLastName()).ifPresent(userFound.get()::setLastName);
            Optional.ofNullable(user.getEmail()).ifPresent(userFound.get()::setEmail);

            Optional.ofNullable(user.getPassword()).ifPresent(userFound.get()::setPassword);
            Optional.ofNullable(user.getConfirmPassword()).ifPresent(userFound.get()::setConfirmPassword);

            if(!Objects.equals(userFound.get().getPassword(), userFound.get().getConfirmPassword())){
                throw new DataIntegrityViolationException("Senha/Usuário incorretos");
            }
            Optional.ofNullable(user.getPhoneNumber()).ifPresent(userFound.get()::setPhoneNumber);
        }
    }

    public void deleteById(UUID id){

        Optional<User> user = Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível achar o id: " + id)));

        repository.deleteById(id);
    }

    public List<User> specification(String firstName,
                                    String lastName,
                                    String email,
                                    String phoneNumber,
                                    Boolean vip){

        Specification<User> specs = Specification
                .where( ((root, query, cb) -> cb.conjunction()) );

        if(firstName != null){
            specs = specs.and(likeFirstName(firstName));
        }

        if(lastName != null){
            specs = specs.and(likeLastName(lastName));
        }

        if(email != null){
            specs = specs.and(equalEmail(email));
        }

        if(phoneNumber != null){
            specs = specs.and(equalPhoneNumber(phoneNumber));
        }

        if(vip != null){
            specs = specs.and(equalVip(vip));
        }
        return repository.findAll(specs);
    }

}
