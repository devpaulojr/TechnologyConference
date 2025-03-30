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

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final CompanyRepository companyRepository;


    public List<Room> findAll(){
        return roomRepository.findAll();
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

}
