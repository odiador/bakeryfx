package co.edu.uniquindio.estructuras.tienda.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Piso implements Serializable {
    private static final long serialVersionUID = 1L;

    // Color principal del piso
    private CakeColor color;
    // Color de las estrellas decorativas
    private StarColor colorEstrella;

    public boolean isEstrella() {
        return colorEstrella != null;
    }
}
