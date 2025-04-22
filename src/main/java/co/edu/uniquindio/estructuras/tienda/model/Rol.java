package co.edu.uniquindio.estructuras.tienda.model;

import lombok.Getter;

public enum Rol {
    CAJERO("Cajero"), ADMINISTRADOR("Administrador");

    @Getter
    private final String nombre;

    Rol(String nombre) {
        this.nombre = nombre;
    }

}
