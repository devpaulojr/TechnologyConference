package com.devpaulojr.technologyconference.services.validations;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.model.Room;
import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RoomValidation {

    private static final List<Room> normalRooms = new ArrayList<>();
    private static final List<Room> vipRooms = new ArrayList<>();

    public static void validate(Room room){

        if(Objects.isNull(room) || Objects.isNull(room.getRoomType())){
            throw new BadRequestException("Valor da sala inválido: " + null);
        }

        String roomType = room.getRoomType().getCode().toUpperCase();

        switch (roomType) {
            case "NORMAL":
                validateNormalRoom(room);
                break;
            case "VIP":
                validateVipRoom(room);
                break;
            default:
                throw new BadRequestException("Valor da sala inválido: " + roomType);
        }
    }

    public static void validateNormalRoom(Room room){

        normalRooms.add(room);

        int roomNumber = normalRooms.size();

        limitNormalRoomValidation(room, roomNumber);
    }

    public static void limitNormalRoomValidation(Room room, Integer roomNumber){

        if(roomNumber <= 5){
            room.setNumberRooms(roomNumber);
            room.setSeatCapacity(400);
            room.setIsOccupied(true);
            room.setRoomStatus(RoomStatus.valueOf("OCUPADO"));
        }

        if(roomNumber > 5){
            normalRooms.removeLast();
            throw new IllegalArgumentException("exceção personalizada");
        }
    }

    public static void validateVipRoom(Room room){

        vipRooms.add(room);

        int roomNumber = vipRooms.size() + 5;

        limitVipRoomValidation(room, roomNumber);
    }

    public static void limitVipRoomValidation(Room room, Integer roomNumber){

        if(roomNumber <= 10){
            room.setNumberRooms(roomNumber);
            room.setSeatCapacity(800);
            room.setIsOccupied(true);
            room.setRoomStatus(RoomStatus.valueOf("OCUPADO"));
        }

        if(roomNumber > 10){
            vipRooms.removeLast();
            throw new IllegalArgumentException("exceção personalizada");
        }
    }
}
