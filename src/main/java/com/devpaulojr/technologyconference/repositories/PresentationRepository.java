package com.devpaulojr.technologyconference.repositories;

import com.devpaulojr.technologyconference.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PresentationRepository extends JpaRepository<Presentation, UUID>, JpaSpecificationExecutor<Presentation> {
}
