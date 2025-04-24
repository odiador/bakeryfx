package co.edu.uniquindio.estructuras.tienda.model;

import lombok.Getter;

@Getter
public enum StarColor {
    AMARILLO("Amarillo", "#FFFF00"),
    BLANCO("Blanco", "#FFFFFF"),
    NARANJA("Naranja", "#FFD580"),
    AZUL("Azul", "#9EF6FF"),
    ROSA("Rosa", "#FFC1E3");

    private final String nombre;
    private final String hex;

    StarColor(String nombre, String hex) {
        this.nombre = nombre;
        this.hex = hex;
    }

    @Override
    public String toString() {
        return nombre;
    }

}