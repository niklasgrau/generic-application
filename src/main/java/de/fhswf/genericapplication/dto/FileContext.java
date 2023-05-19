package de.fhswf.genericapplication.dto;

public record FileContext(Long typeId, Long id, String propertyName) {

    @Override
    public String toString() {
        return "FileContext{" +
                "typeId='" + typeId + '\'' +
                ", id=" + id +
                ", propertyName='" + propertyName + '\'' +
                '}';
    }
}
