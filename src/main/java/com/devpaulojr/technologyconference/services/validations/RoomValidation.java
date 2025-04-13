package com.devpaulojr.technologyconference.services.validations;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.model.Room;
import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import com.devpaulojr.technologyconference.model.enums.RoomType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        limitNormalRoomValidation(room);
    }

    public static void limitNormalRoomValidation(Room room) {

        if (normalRooms.size() > 2) {
            normalRooms.removeLast();
            throw new IllegalArgumentException("exceção personalizada");
        }

        if (normalRooms.isEmpty()) {
            room.setNumberRooms(1);
            room.setSeatCapacity(400);
            room.setIsOccupied(true);
            room.setRoomStatus(RoomStatus.OCUPADO);
            normalRooms.add(room);
            return;
        }

        List<Integer> existingNumbers = normalRooms
                .stream()
                .map(Room::getNumberRooms)
                .filter(Objects::nonNull)
                .sorted()
                .toList();

        int roomNumber = 1;
        for (Integer value : existingNumbers) {

            if (Objects.equals(value, roomNumber)) {
                roomNumber++;
            }
        }

        room.setNumberRooms(roomNumber);
        room.setSeatCapacity(400);
        room.setIsOccupied(true);
        room.setRoomStatus(RoomStatus.OCUPADO);
        normalRooms.add(room);
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

    public static void validateDeleteById(UUID id, RoomType roomType){

        if(Objects.equals(roomType.getCode(), "NORMAL")){

            for(int line = 0; line < normalRooms.size(); line++){

                var idCurrent = normalRooms.get(line).getId();

                if(Objects.equals(idCurrent, id)){
                    normalRooms.remove(line);
                    break;
                }
            }
        }

        if(Objects.equals(roomType.getCode(), "VIP")){

            for(int line = 0; line < vipRooms.size(); line++){

                var idCurrent = vipRooms.get(line).getId();

                if(Objects.equals(idCurrent, id)){
                    vipRooms.remove(line);
                    break;
                }
            }
        }
    }

    public static void validateDeleteAll(){
        normalRooms.clear();
        vipRooms.clear();
    }
}
