package com.devpaulojr.technologyconference.repositories.specs;

import com.devpaulojr.technologyconference.model.Presentation;
import org.springframework.data.jpa.domain.Specification;

public class PresentationSpecification {

    public static Specification<Presentation> likeName(String name){
        return ((root, query, cb) ->
                cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
    }

    public static Specification<Presentation> equalStartTimeHour(Integer hour){
        return ((root, query, cb) ->
                cb.equal(
                        cb.function("to_char",
                                String.class,
                                root.get("startTime"),
                                cb.literal("HH24")),
                        String.format("%02d", hour)));
    }
}
