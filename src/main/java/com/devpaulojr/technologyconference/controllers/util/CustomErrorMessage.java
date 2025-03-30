package com.devpaulojr.technologyconference.controllers.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public interface CustomErrorMessage {

    default String errorMessageConflict(String message){

        Pattern pattern = Pattern.compile("violates unique constraint \"(.*?)\"");

        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            return matcher.group(1);
        }
        return "Desconhecido";
    }

    default String errorMessageDetailsConflict(String message){

        Pattern pattern = Pattern.compile("ERROR: duplicate key value violates unique constraint \"([^\"]+)\"");

        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            return matcher.group();
        }
        return "Desconhecido";
    }
}
