package com.devpaulojr.technologyconference.repositories;

import com.devpaulojr.technologyconference.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
