package com.devpaulojr.technologyconference.services.validations;

import com.devpaulojr.technologyconference.controllers.exceptions.BadRequestException;
import com.devpaulojr.technologyconference.model.Presentation;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;


@Component
public class PresentationValidation {

    public static void presentationValidation(Presentation presentation, List<Presentation> presentationList){

        presentation.setEndTime(presentation.getStartTime().plusHours(2));

        boolean existing = presentationList
                .stream()
                .anyMatch(data -> {

                    OffsetDateTime start = data.getStartTime();
                    OffsetDateTime end = data.getEndTime();

                   return start.isBefore(presentation.getEndTime()) && end.isAfter(presentation.getStartTime());
                });

        if(existing){
            throw new BadRequestException("existe uma data no intervalo");
        }
    }
}
