package co.edu.uniquindio.estructuras.tienda.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import co.edu.uniquindio.estructuras.tienda.services.Imagenable;
import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Producto implements Comparable<Producto>, Imagenable, Serializable {
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

	/**
	 * Guarda la imagen en la carpeta images/ y almacena la ruta relativa.
	 * @param archivoImagen El archivo original seleccionado por el usuario.
	 * @throws IOException Si ocurre un error al copiar el archivo.
	 */
	public void setImagen(File archivoImagen) throws IOException {
		String extension = archivoImagen.getName().substring(archivoImagen.getName().lastIndexOf('.'));
		String nombreDestino = "images/" + java.util.UUID.randomUUID() + extension;
		java.nio.file.Path destino = java.nio.file.Paths.get(nombreDestino);
		java.nio.file.Files.copy(archivoImagen.toPath(), destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		this.rutaImagen = nombreDestino;
	}

	public Image getImagenFX() {
		if (rutaImagen != null) {
			return new Image(new File(rutaImagen).toURI().toString());
		}
		return null;
	}

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
