package com.devpaulojr.technologyconference.controllers.exceptions.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface CustomErrorMessage {

    default String errorMessage(String message){

        Pattern pattern = Pattern.compile("violates unique constraint \"(.*?)\"");

        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            return matcher.group(1);
        }
        return "Desconhecido";
    }

    default String errorMessageDetails(String message){

        Pattern pattern = Pattern.compile("ERROR: duplicate key value violates unique constraint \"([^\"]+)\"");

        Matcher matcher = pattern.matcher(message);

        if(matcher.find()){
            return matcher.group();
        }
        return "Desconhecido";
    }
}
