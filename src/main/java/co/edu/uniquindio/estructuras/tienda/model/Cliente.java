package co.edu.uniquindio.estructuras.tienda.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoDuplicadoException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNuloException;
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

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Builder
@ToString
public class Cliente implements Imagenable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	@NonNull
	private String identificacion;
	@NonNull
	private String nombre, direccion;
	@NonNull
	@Builder.Default
	private ArrayList<Venta> lstVentas = new ArrayList<Venta>();
	@ToString.Exclude
	@Builder.Default
	private String rutaImagen = "images/default.png";

	@Override
	public String getRutaImagen() {
		return rutaImagen;
	}

	@NonNull
	@Builder.Default
	private ArrayList<CarritoCompras> carrito = new ArrayList<CarritoCompras>();

	/**
	 * Guarda la imagen en la carpeta userimages/ y almacena la ruta relativa.
	 * 
	 * @param rutaImagen Ruta original seleccionada por el usuario (puede ser file:
	 *                   o ruta local).
	 * @throws IOException Si ocurre un error al copiar el archivo.
	 */
	public void setImagen(String rutaImagen) throws IOException {
		if (rutaImagen == null || rutaImagen.trim().isEmpty()) {
			throw new IOException("La ruta de la imagen es nula o vacía.");
		}
		java.io.File archivoImagen;
		if (rutaImagen.startsWith("file:")) {
			try {
				archivoImagen = new java.io.File(new java.net.URI(rutaImagen.replace("\\", "/")));
			} catch (Exception e) {
				throw new IOException("No se pudo convertir la URL de la imagen a archivo local.", e);
			}
		} else {
			archivoImagen = new java.io.File(rutaImagen);
		}
		if (!archivoImagen.exists()) {
			throw new IOException("El archivo de imagen no existe: " + archivoImagen.getAbsolutePath());
		}
		String extension = archivoImagen.getName().substring(archivoImagen.getName().lastIndexOf('.'));
		String nombreDestino = "userimages/" + java.util.UUID.randomUUID() + extension;
		java.nio.file.Path destino = java.nio.file.Paths.get(nombreDestino);
		try {
			java.nio.file.Files.createDirectories(destino.getParent());
			java.nio.file.Files.copy(archivoImagen.toPath(), destino,
					java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			this.rutaImagen = nombreDestino;
		} catch (IOException ex) {
			throw new IOException(
					"No se pudo copiar la imagen.\nOrigen: " + archivoImagen.getAbsolutePath() +
							"\nDestino: " + destino.toAbsolutePath() +
							"\nCausa: " + ex.getMessage(),
					ex);
		}
	}

	public Image getImagenFX() {
		if (rutaImagen != null) {
			return new Image(new java.io.File(rutaImagen).toURI().toString());
		}
		return null;
	}

	public boolean agregarCarrito(CarritoCompras carritos) throws ElementoNuloException, ElementoNoEncontradoException {
		if (carritos != null) {
			return agregarCarritoAux(carritos);
		}
		throw new ElementoNuloException("El carrito es nulo");
	}

	private boolean agregarCarritoAux(CarritoCompras carrito2) throws ElementoNoEncontradoException {
		if (!carrito.contains(carrito2)) {
			return carrito.add(carrito2);
		} else {
			throw new ElementoNoEncontradoException("El objeto no se ha podido encontrar");
		}
	}

	public boolean borrarCarrito(CarritoCompras carrito) throws ElementoNuloException, ElementoNoEncontradoException {
		if (carrito != null) {
			return borrarCarritoAux(carrito);
		}
		throw new ElementoNuloException("Carrito es nulo");
	}

	private boolean borrarCarritoAux(CarritoCompras carrito2) throws ElementoNoEncontradoException {
		if (!carrito.remove(carrito2)) {
			throw new ElementoNoEncontradoException("No se ha encontrado el carrito");
		}
		return true;
	}

	public CarritoCompras buscarCarritos(String codigo) throws ElementoNoEncontradoException {
		Iterator<CarritoCompras> iterador = carrito.iterator();
		while (iterador.hasNext()) {
			CarritoCompras carritoAux = iterador.next();
			if (carritoAux.getCodigo().equals(codigo)) {
				return carritoAux;
			}
		}
		throw new ElementoNoEncontradoException("No se ha encontrado el carrito con este codigo");
	}

	public void actualizarCarrito(CarritoCompras carro) {
		Iterator<CarritoCompras> iterador = carrito.iterator();
		while (iterador.hasNext()) {
			CarritoCompras carroAux = iterador.next();
			if (carro.equals(carroAux)) {
				carrito.remove(carroAux);
				carrito.add(carro);
			}
		}
	}

	public String leerCarritos() {
		return carrito.toString();
	}

	public void agregarVenta(Venta venta) throws ElementoDuplicadoException {
		if (lstVentas.contains(venta)) {
			throw new ElementoDuplicadoException("La venta ya se encuentra registrada");
		} else {
			lstVentas.add(venta);
		}
	}

}
