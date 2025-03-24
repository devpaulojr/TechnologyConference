package com.devpaulojr.technologyconference.services;

import com.devpaulojr.technologyconference.model.Room;
import com.devpaulojr.technologyconference.model.User;
import com.devpaulojr.technologyconference.repositories.CompanyRepository;
import com.devpaulojr.technologyconference.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final CompanyRepository companyRepository;


    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public Room insert(Room room){

        var company = companyRepository.findById(room.getCompany().getId());

        if(company.isPresent()){
            room.setCompany(company.get());
            room.getCompany().setUsers(company.get().getUsers());
        }
        return roomRepository.save(room);
    }
}
