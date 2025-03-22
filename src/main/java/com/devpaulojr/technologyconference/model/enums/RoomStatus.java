package com.devpaulojr.technologyconference.model.enums;

import lombok.Getter;

@Getter
public enum RoomStatus {

    DISPONIVEL("DISPONIVEL"),
    EM_MANUTENCAO("EM_MANUTENCAO"),
    RESERVADO("RESERVADO"),
    OCUPADO("OCUPADO");

    private final String code;

    private RoomStatus(String code){
        this.code = code;
    }

    public static RoomStatus fromValue(String code){
        for(RoomStatus value : RoomStatus.values()){
            String currentValue = value.getCode();
            if(currentValue.equals(code)){
                return value;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + code);
    }
}
