package co.edu.uniquindio.estructuras.tienda.model;

import java.util.HashMap;
import java.util.TreeSet;

import co.edu.uniquindio.estructuras.tienda.exceptions.CarritoNoFuncionaException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoDuplicadoException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNuloException;
import co.edu.uniquindio.estructuras.tienda.exceptions.VentaNoFuncionaException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tienda {
	@Builder.Default
	@NonNull
	private HashMap<String, Usuario> mapUsuarios = new HashMap<>();
	@NonNull
	private String nombre, direccion, nit;
	@NonNull
	private TreeSet<Producto> treeProductos;
	@NonNull
	private HashMap<String, Venta> mapVentas;
	@NonNull
	private HashMap<String, Cliente> mapClientes;

	public void agregarUsuario(Usuario usuario) throws ElementoDuplicadoException, ElementoNuloException {
		if (usuario == null)
			throw new ElementoNuloException("El usuario es nulo");
		if (mapUsuarios.containsKey(usuario.getCorreo()))
			throw new ElementoDuplicadoException("El usuario ya existe");
		mapUsuarios.put(usuario.getCorreo(), usuario);
	}

	public Usuario buscarUsuario(String correo) throws ElementoNoEncontradoException {
		Usuario usuario = mapUsuarios.get(correo);
		if (usuario == null)
			throw new ElementoNoEncontradoException("Usuario no encontrado");
		return usuario;
	}

	public Usuario autenticarUsuario(String correo, String contrasena) throws ElementoNoEncontradoException {
		Usuario usuario = buscarUsuario(correo);
		if (!usuario.getContrasena().equals(contrasena))
			throw new ElementoNoEncontradoException("Contraseña incorrecta");
		return usuario;
	}

	public HashMap<String, Usuario> getMapUsuarios() {
		return mapUsuarios;
	}

	public TreeSet<Producto> getTreeProductos() {
		return treeProductos;
	}

	public void setTreeProductos(TreeSet<Producto> productos) {
		this.treeProductos = productos;
	}

	public boolean agregarProducto(Producto producto) throws ElementoNuloException {
		if (producto != null) {
			return treeProductos.add(producto);
		}
		throw new ElementoNuloException("El producto es nulo");
	}

	public boolean eliminarProducto(Producto producto) throws ElementoNuloException, ElementoNoEncontradoException {
		if (producto != null)
			return eliminarProductoAux(producto);
		throw new ElementoNuloException("El producto es nulo");
	}

	private boolean eliminarProductoAux(Producto producto) throws ElementoNoEncontradoException {
		if (!treeProductos.remove(producto))
			throw new ElementoNoEncontradoException("No se ha encontrado el producto");
		return true;
	}

	public Producto buscarProducto(String codigo) throws ElementoNoEncontradoException {
		for (Producto producto : treeProductos) {
			if (producto.getCodigo().equals(codigo)) {
				return producto;
			}
		}
		throw new ElementoNoEncontradoException("Producto No Encontrado");
	}

	public void actualizarProducto(Producto producto) throws ElementoNuloException, ElementoNoEncontradoException {
		if (producto == null)
			throw new ElementoNuloException("EL producto no existe");
		if (!treeProductos.remove(producto)) {
			throw new ElementoNoEncontradoException("No se ha encontrado el producto a actualizar");
		}
		treeProductos.add(producto);
	}

	public void agregarVenta(Venta venta) throws ElementoNuloException, ElementoDuplicadoException,
			VentaNoFuncionaException, ElementoNoEncontradoException {
		if (venta == null)
			throw new ElementoNuloException("La venta es nula");
		if (mapVentas.containsKey(venta.getCodigo()))
			throw new ElementoDuplicadoException("La venta ya se encuentra registrada");
		verificarVenta(venta);
		for (DetalleVenta detalleVenta : venta.getLstDetalleVentas()) {
			venderProductoAux(detalleVenta.getProducto(), detalleVenta.getCantVendida());
		}
		mapVentas.put(venta.getCodigo(), venta);
	}

	private void venderProductoAux(@NonNull Producto producto, int cantVendida)
			throws ElementoNuloException, ElementoNoEncontradoException {
		producto.venderCantidad(cantVendida);
		if (producto instanceof Pastel) {
			System.out.println("Pastel personalizado no encontrado en inventario, permitiendo: "
					+ producto.getCodigo() + " (" + producto.getNombre() + ")");
		} else {
			actualizarProducto(producto);
		}
	}

	public boolean eliminarVenta(Venta venta) throws ElementoNoEncontradoException, ElementoNuloException {
		if (venta == null)
			throw new ElementoNuloException("La venta es nula");
		return eliminarVentaAux(venta);
	}

	private boolean eliminarVentaAux(Venta venta) throws ElementoNoEncontradoException {
		if (mapVentas.remove(venta.getCodigo()) == null)
			throw new ElementoNoEncontradoException("No se ha encontrado la venta");
		return true;
	}

	public Venta buscarVenta(String codigo) throws ElementoNoEncontradoException {
		Venta venta = mapVentas.get(codigo);
		if (venta != null)
			return venta;
		throw new ElementoNoEncontradoException("No se ha encontrado la venta");
	}

	public void actualizarVenta(Venta venta) throws ElementoNoEncontradoException {
		if (venta == null)
			throw new ElementoNoEncontradoException("La venta es nula");
		if (mapVentas.containsKey(venta.getCodigo())) {
			mapVentas.put(venta.getCodigo(), venta);
			return;
		}
		throw new ElementoNoEncontradoException("No se ha encontrado la venta a actualizar");
	}

	public void agregarCliente(Cliente cliente) throws ElementoNuloException, ElementoDuplicadoException {
		if (cliente == null)
			throw new ElementoNuloException("El cliente es nulo");
		agregarClienteAux(cliente);

	}

	private void agregarClienteAux(Cliente cliente) throws ElementoDuplicadoException {
		if (!mapClientes.containsKey(cliente.getIdentificacion())) {
			mapClientes.put(cliente.getIdentificacion(), cliente);
			return;
		}
		throw new ElementoDuplicadoException("El cliente ya existe en la tienda");

	}

	public void eliminarCliente(Cliente cliente) throws ElementoNuloException, ElementoNoEncontradoException {
		if (cliente == null)
			throw new ElementoNuloException("Recuerda seleccionar un cliente");
		eliminarClienteAux(cliente);
	}

	private void eliminarClienteAux(Cliente cliente) throws ElementoNoEncontradoException {
		if (mapClientes.containsKey(cliente.getIdentificacion())) {
			mapClientes.remove(cliente.getIdentificacion());
			return;
		}
		throw new ElementoNoEncontradoException("No se ha encontrado el cliente a eliminar");

	}

	public Cliente buscarCliente(String cedula) throws ElementoNoEncontradoException {
		Cliente cliente = mapClientes.get(cedula);
		if (cliente == null)
			throw new ElementoNoEncontradoException("El cliente no ha sido encontrado");
		return cliente;
	}

	public void actualizarCliente(Cliente cliente) throws ElementoNuloException, ElementoNoEncontradoException {
		if (cliente == null)
			throw new ElementoNuloException("El cliente a actualizar es nulo");
		Cliente clienteAux = mapClientes.get(cliente.getIdentificacion());
		if (clienteAux == null)
			throw new ElementoNoEncontradoException("No se ha encontrado el cliente a actualizar");
		clienteAux.setDireccion(cliente.getDireccion());
		clienteAux.setNombre(cliente.getNombre());
		clienteAux.setRutaImagen(cliente.getRutaImagen());
		mapClientes.put(cliente.getIdentificacion(), clienteAux);
		return;

	}

	public void actualizarClienteCompleto(Cliente cliente) throws ElementoNoEncontradoException, ElementoNuloException {
		if (cliente == null)
			throw new ElementoNuloException("El cliente a actualizar es nulo");
		Cliente clienteAux = mapClientes.get(cliente.getIdentificacion());
		if (clienteAux == null)
			throw new ElementoNoEncontradoException("No se ha encontrado el cliente a actualizar");
		mapClientes.put(cliente.getIdentificacion(), cliente);
		return;

	}

	public void agregarCarritoCliente(String cedula, CarritoCompras carrito)
			throws ElementoNuloException, ElementoNoEncontradoException {
		buscarCliente(cedula).agregarCarrito(carrito);
	}

	public String leerClientes() {
		return mapClientes.toString();
	}

	public void verificarCarrito(CarritoCompras carrito) throws CarritoNoFuncionaException {
		for (DetalleCarrito detalle : carrito.getLstDetalleCarritos())
			if (!verificarDetalle(detalle))
				throw new CarritoNoFuncionaException("El carrito contiene productos que no tienen stock");
	}

	public boolean verificarDetalle(DetalleCarrito detalle) {
		Producto productoBuscar = detalle.getProducto();
		for (Producto producto : treeProductos) {
			if (producto.getCodigo().equals(productoBuscar.getCodigo())) {
				return producto.verificarCantidad(detalle.getCantSeleccionada());
			}
		}
		// Si el producto es un pastel personalizado, permitimos que pase la
		// verificación
		if (productoBuscar instanceof Pastel) {
			System.out.println("Pastel personalizado no encontrado en inventario, permitiendo: "
					+ productoBuscar.getCodigo() + " (" + productoBuscar.getNombre() + ")");
			return true;
		}
		System.out.println("No se encontró el producto en inventario: " + productoBuscar.getCodigo() + " ("
				+ productoBuscar.getNombre() + ")");
		return false;
	}

	public void verificarVenta(Venta venta) throws VentaNoFuncionaException {
		for (DetalleVenta detalle : venta.getLstDetalleVentas())
			if (!verificarDetalleVenta(detalle))
				throw new VentaNoFuncionaException("La venta contiene productos que no tienen stock");
	}

	private boolean verificarDetalleVenta(DetalleVenta detalle) {
		Producto productoBuscar = detalle.getProducto();
		for (Producto producto : treeProductos) {
			if (producto.getCodigo().equals(productoBuscar.getCodigo())) {
				return producto.verificarCantidad(detalle.getCantVendida());
			}
		}
		// Si el producto es un pastel personalizado, permitimos que pase la
		// verificación
		if (productoBuscar instanceof Pastel) {
			System.out.println("Pastel personalizado no encontrado en inventario, permitiendo: "
					+ productoBuscar.getCodigo() + " (" + productoBuscar.getNombre() + ")");
			return true;
		}
		System.out.println("No se encontró el producto en inventario: " + productoBuscar.getCodigo() + " ("
				+ productoBuscar.getNombre() + ")");
		return false;
	}

}
