package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.controllers.exceptions.ResourceNotFoundException;
import com.devpaulojr.technologyconference.model.Room;
import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import com.devpaulojr.technologyconference.repositories.CompanyRepository;
import com.devpaulojr.technologyconference.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.devpaulojr.technologyconference.services.validations.RoomValidation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final CompanyRepository companyRepository;


    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room findById(UUID id){
        return roomRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Não foi possível achar o id: " + id));
    }

    public Room insert(Room room) {

        if(room.getRoomType() == null){
            throw new BadRequestException("Valor da sala inválido: " + null);
        }

        if (room.getCompany() != null && room.getCompany().getId() != null) {
            var company = companyRepository.findById(room.getCompany().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Não foi possível achar o id: " + room.getCompany().getId()));

            room.setCompany(company);

            //Validação da classe "Room"
            validate(room);
        }

        if (room.getCompany() != null && (room.getCompany()).getUsers().isEmpty()) {
            room.setRoomStatus(RoomStatus.valueOf("RESERVADO"));
        }
        return roomRepository.save(room);
    }

    public void update(Room room, UUID id){

        Optional<Room> roomFound = roomRepository.findById(id);

        if(roomFound.isPresent()){
            updated(roomFound, room);
            roomRepository.save(roomFound.get());
            return;
        }
        throw new ResourceNotFoundException("Não foi possível achar o id: " + id);
    }

    private void updated(Optional<Room> roomFound , Room room) {
        roomFound.ifPresent(value -> Optional.ofNullable(room.getRoomStatus()).ifPresent(value::setRoomStatus));
    }

    public void deleteById(UUID id){

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room não encontrado"));

        //Validação do ‘ID’, e o tipo da lista para remoção da classe 'Room'
        validateDeleteById(id, room.getRoomType());

        if (room.getCompany() != null) {
            room.getCompany().setRoom(null);
            room.setCompany(null);
        }
        roomRepository.delete(room);
    }

    public void deleteAll(){

        List<Room> rooms = roomRepository.findAll();

        for (Room room : rooms) {
            room.getCompany().setRoom(null);
            room.setCompany(null);
        }
        validateDeleteAll();

        roomRepository.deleteAll(rooms);
    }
}
