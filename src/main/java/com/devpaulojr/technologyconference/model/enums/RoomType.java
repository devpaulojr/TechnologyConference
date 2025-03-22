package com.devpaulojr.technologyconference.model.enums;

import lombok.Getter;

@Getter
public enum RoomType {

    NORMAL("NORMAL"),
    VIP("VIP");

    private final String code;

    private RoomType(String code){
        this.code = code;
    }

    public static RoomType fromValue(String code){
        for(RoomType value : RoomType.values()){
            String currentValue = value.getCode();
            if(currentValue.equals(code)){
                return value;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + code);
    }
}
