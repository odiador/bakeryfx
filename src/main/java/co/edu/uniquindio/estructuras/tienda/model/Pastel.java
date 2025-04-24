package co.edu.uniquindio.estructuras.tienda.model;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Pastel extends Producto {
    private static final long serialVersionUID = 1L;
    private Piso[] pisos = new Piso[3];

    /**
     * Crea un pastel y calcula el precio y nombre automáticamente.
     * 
     * @param pisos array de pisos (de 1 a 3)
     */
    public Pastel(@NonNull Piso[] pisos) {
        super();
        this.pisos = pisos;
        int cantidadPisos = pisos.length;
        double precioBase = 0;
        switch (cantidadPisos) {
            case 1:
                precioBase = 18000;
                break;
            case 2:
                precioBase = 32000;
                break;
            case 3:
                precioBase = 40000;
                break;
            default:
                precioBase = 0;
        }
        double precio = precioBase;
        StringBuilder nombreBuilder = new StringBuilder("Pastel ");
        for (int i = 0; i < cantidadPisos; i++) {
            Piso piso = pisos[i];
            boolean estrellas = piso != null && piso.isEstrella();
            if (i > 0)
                nombreBuilder.append(", ");
            nombreBuilder.append(piso != null && piso.getColor() != null ? piso.getColor() : "?");
            if (estrellas)
                nombreBuilder.append("*");
            if (estrellas)
                precio *= 1.1;
        }
        setNombre(nombreBuilder.toString());
        setPrecio(precio);
        setCantidad(1);
        setCodigo(UUID.randomUUID().toString());
    }
}
