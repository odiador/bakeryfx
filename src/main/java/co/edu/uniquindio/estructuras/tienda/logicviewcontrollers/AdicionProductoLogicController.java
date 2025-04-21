package co.edu.uniquindio.estructuras.tienda.logicviewcontrollers;

import java.io.File;
import java.util.UUID;

import co.edu.uniquindio.estructuras.tienda.application.TiendaMain;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import lombok.NonNull;

public class AdicionProductoLogicController {
	private static AdicionProductoLogicController instance;

	public static AdicionProductoLogicController getInstance() {
		if (instance == null)
			instance = new AdicionProductoLogicController();
		return instance;
	}

	private @NonNull Image image;

	public void agregarAction(String nombre, String precio, String cantidad) {
		try {
			if (image == null) {
				new Alert(AlertType.WARNING, "Debes seleccionar una imagen para el producto.").show();
				return;
			}
			ModelFactoryController.getInstance().agregarProducto(UUID.randomUUID().toString(), nombre.trim(), precio,
					cantidad, image);
			new Alert(AlertType.CONFIRMATION, String.format("El producto %s ha sido registrado con éxito", nombre))
				.show();
		} catch (Exception e) {
			e.printStackTrace();
			new Alert(AlertType.WARNING, e.getMessage()).show();
		}
	}

	public void seleccionarImgAction(ImageView imgviewCliente) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar imagen del producto");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
		);
		File selectedFile = fileChooser.showOpenDialog(TiendaMain.getCurrentStage());
		if (selectedFile != null) {
			Image img = new Image(selectedFile.toURI().toString());
			imgviewCliente.setImage(img);
			this.image = img;
		}
	}
}
