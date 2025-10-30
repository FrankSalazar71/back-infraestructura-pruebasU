package pe.edu.vallegrande.ms_infraestructura.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BoxType {
    CAÑO("CAÑO"),
    BOMBA("BOMBA"),
    OTRO("OTRO");

    private final String value;

    BoxType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static BoxType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (BoxType type : BoxType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para BoxType: " + value + 
            ". Valores válidos: CAÑO, BOMBA, OTRO");
    }
}