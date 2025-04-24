package co.edu.uniquindio.estructuras.tienda.model;

import java.io.Serializable;
import co.edu.uniquindio.estructuras.tienda.services.Imagenable;
import lombok.AllArgsConstructor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@lombok.experimental.SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public abstract class Producto implements Comparable<Producto>, Imagenable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NonNull
	@EqualsAndHashCode.Include
	private String codigo;
	private String nombre;
	private double precio;
	private int cantidad;
	@ToString.Exclude
	private String rutaImagen;

	@Override
	public int compareTo(Producto o) {
		return codigo.compareTo(o.codigo);
	}

	public boolean verificarCantidad(int cantSeleccionada) {
		return this.cantidad >= cantSeleccionada;
	}

	public void venderCantidad(int cant) {
		this.cantidad -= cant;
	}
}
