package co.edu.uniquindio.estructuras.tienda.model;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Usuario implements Serializable {
    @EqualsAndHashCode.Include
    private String correo;
    private String nombre;
    private String contrasena;
    private Rol rol;
    private boolean verificado;
}
