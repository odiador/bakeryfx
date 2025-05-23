package co.edu.uniquindio.estructuras.tienda.logicviewcontrollers;

import java.io.IOException;

import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.DetalleCarrito;
import co.edu.uniquindio.estructuras.tienda.model.Producto;
import co.edu.uniquindio.estructuras.tienda.services.ICloseableController;
import co.edu.uniquindio.estructuras.tienda.services.IProductoController;
import co.edu.uniquindio.estructuras.tienda.utils.Constants;
import co.edu.uniquindio.estructuras.tienda.utils.FxmlPerspective;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class ProductViewLogicController {

	private SimpleObjectProperty<Producto> productoProperty;
	private SimpleObjectProperty<DetalleCarrito> detalleCarritoProperty;
	/**
	 * El uso normal es el uso del producto, si se da clic agregaría el producto al
	 * carrito, si no es normal se usa un detalle y cuando se de clic se elimina un
	 * detalle del carrito
	 */
	private boolean normalUse = true;

	public ProductViewLogicController() {
		productoProperty = new SimpleObjectProperty<Producto>();
		detalleCarritoProperty = new SimpleObjectProperty<DetalleCarrito>();
	}

	public void setProducto(Producto p) {
		Platform.runLater(() -> productoProperty.setValue(p));
	}

	public void setDetalleCarrito(DetalleCarrito d) {
		Platform.runLater(() -> detalleCarritoProperty.setValue(d));
	}

	public void cargarProductoLabels(Label lblNombre, Label lblPrecio, Label lblStock, BorderPane root,
			Label lblHover, SVGPath svgHover) {
		normalUse = true;
		lblHover.setText("Agregar al Carrito");
		svgHover.setContent(Constants.SHOPPING_CARD_CONTENT);
		productoProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
				setProductValues(lblNombre, lblPrecio, lblStock, newValue, root);
		});
		if (productoProperty.getValue() != null)
			setProductValues(lblNombre, lblPrecio, lblStock, productoProperty.getValue(), root);
	}

	public void cargarDetailLabels(Label lblNombre, Label lblPrecio, Label lblStock, BorderPane root, Label lblHover,
			SVGPath svgHover) {
		normalUse = false;
		lblHover.setText("Eliminar");
		svgHover.setContent(Constants.RECYCLE_BIN_CONTENT);
		detalleCarritoProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
				setDetailValues(lblNombre, lblPrecio, lblStock, newValue, root);
		});
		if (detalleCarritoProperty.getValue() != null)
			setDetailValues(lblNombre, lblPrecio, lblStock, detalleCarritoProperty.getValue(), root);
	}

	private void setDetailValues(Label lblNombre, Label lblPrecio, Label lblInfo, DetalleCarrito newValue,
			BorderPane root) {
		lblNombre.setText(newValue.getProducto().getNombre());
		lblPrecio.setText(String.format("$%.2f C/U", newValue.getProducto().getPrecio()));
		lblInfo.setText(String.format("%d seleccionados de %d", newValue.getCantSeleccionada(),
				newValue.getProducto().getCantidad()));
		ImageView imageView = buildCroppedRoundedImageView(newValue.getProducto().getImageFX(), 80, 18);
		root.setCenter(imageView);
	}

	private void setProductValues(Label lblNombre, Label lblPrecio, Label lblStock, Producto producto,
			BorderPane root) {
		lblNombre.setText(producto.getNombre());
		lblPrecio.setText(String.format("$%.2f C/U", producto.getPrecio()));
		lblStock.setText(String.format("%d disponibles", producto.getCantidad()));
		ImageView imageView = buildCroppedRoundedImageView(producto.getImageFX(), 80, 18);
		root.setCenter(imageView);
	}

	/**
	 * Crea un ImageView cuadrado con esquinas redondeadas para mostrar imágenes de producto.
	 * @param image Imagen a mostrar
	 * @param size Tamaño del lado (px)
	 * @param arc Radio de redondeo de las esquinas (px)
	 * @return ImageView listo para usar en la UI
	 */
	private ImageView buildCroppedRoundedImageView(javafx.scene.image.Image image, double size, double arc) {
		ImageView imageView = new ImageView(image);
		// Crop cuadrado centrado
		double imgW = image.getWidth();
		double imgH = image.getHeight();
		double side = Math.min(imgW, imgH);
		double x = (imgW - side) / 2.0;
		double y = (imgH - side) / 2.0;
		imageView.setViewport(new javafx.geometry.Rectangle2D(x, y, side, side));
		imageView.setFitWidth(size);
		imageView.setFitHeight(size);
		imageView.setPreserveRatio(false); // Forzar cuadrado
		javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(size, size);
		clip.setArcWidth(arc);
		clip.setArcHeight(arc);
		imageView.setClip(clip);
		return imageView;
	}

	public void unhoverAction(Pane layerAgregar) {
		FadeTransition fade = new FadeTransition(Duration.millis(150), layerAgregar);
		fade.setFromValue(1);
		fade.setToValue(0);
		fade.playFromStart();
	}

	public void hoverAction(Pane layerAgregar) {
		FadeTransition fade = new FadeTransition(Duration.millis(150), layerAgregar);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.playFromStart();
	}

	public void irAAgregarAction() {
		if (normalUse)
			irASeleccionarCantidad();
		else
			eliminarElementoCarrito();
	}

	private void eliminarElementoCarrito() {
		try {
			if (new Alert(AlertType.INFORMATION, "¿Deseas eliminar este producto del carrito?", ButtonType.YES,
					ButtonType.NO).showAndWait().orElse(null) != ButtonType.YES)
				return;
			ModelFactoryController.getInstance().eliminarDetalleCarrito(detalleCarritoProperty.getValue());
		} catch (ElementoNoEncontradoException e) {
			new Alert(AlertType.WARNING, e.getMessage()).show();
		}
	}

	private void irASeleccionarCantidad() {
		if (productoProperty.getValue().getCantidad() <= 0) {
			new Alert(AlertType.WARNING, "El producto no tiene existencias selecciona otro producto").show();
			return;
		}
		try {
			FxmlPerspective perspective = FxmlPerspective.loadPerspective("addCarritoCant");
			((IProductoController) perspective.getController()).setProducto(productoProperty.getValue());
			((ICloseableController) perspective.getController()).setCloseMethod(() -> {
				InventarioLogicController.getInstance().irAInventario();
			});
			MenuPrincipalLogicController.getInstance().cambiarPerspectiva(perspective);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
