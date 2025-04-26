package com.devpaulojr.technologyconference.repositories.specs;

import com.devpaulojr.technologyconference.model.Room;
import com.devpaulojr.technologyconference.model.enums.RoomStatus;
import com.devpaulojr.technologyconference.model.enums.RoomType;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {

    public static Specification<Room> equalNumberRooms(Integer numberRooms){
        return ((root, query, cb) ->
                cb.equal(root.get("numberRooms"), numberRooms));
    }

    public static Specification<Room> likeRoomStatus(RoomStatus roomStatus){
        return ((root, query, cb) ->
                cb.like(root.get("roomStatus"), "%" + roomStatus.getCode() + "%"));
    }

    public static Specification<Room> likeRoomType(RoomType roomType){
        return ((root, query, cb) ->
                cb.like(root.get("roomType"),"%" + roomType.getCode() + "%"));
    }
}
