package co.edu.uniquindio.estructuras.tienda.model;

import lombok.Getter;

@Getter
public enum CakeColor {
    CELESTE("Celeste", "#9efcff"),
    ROSA("Rosa", "#ffb6d9"),
    LILA("Lila", "#c6b6ff"),
    VERDE_MENTA("Verde menta", "#b6ffd0"),
    DURAZNO("Durazno", "#ffe4b6");

    private final String nombre;
    private final String hex;

    CakeColor(String nombre, String hex) {
        this.nombre = nombre;
        this.hex = hex;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
