package co.edu.uniquindio.estructuras.tienda.logicviewcontrollers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import co.edu.uniquindio.estructuras.tienda.application.TiendaMain;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.Cliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ActualizarClienteLogicController {
	private static ActualizarClienteLogicController instance;

	public static ActualizarClienteLogicController getInstance() {
		if (instance == null)
			instance = new ActualizarClienteLogicController();
		return instance;
	}

	private Image image;
private String imagePath;

	public void setCliente(Cliente c, Label lblIdentificacion, TextField tfDireccion, TextField tfNombre,
			ImageView imgviewCliente) {
		lblIdentificacion.setText(c.getIdentificacion());
		tfDireccion.setText(c.getDireccion());
		tfNombre.setText(c.getNombre());
		this.image = c.getImageFX();
		imgviewCliente.setImage(this.image);
		this.imagePath = c.getRutaImagen(); // Por defecto la actualFX());
	}

	public void seleccionarImgAction(ImageView imgviewCliente) {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(TiendaMain.getCurrentStage());
		if (selectedFile != null) {
			try {
				InputStream targetStream = new DataInputStream(new FileInputStream(selectedFile));
				image = new Image(targetStream);
				imgviewCliente.setImage(image);
				imagePath = selectedFile.getAbsolutePath();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void quitarSeleccionImg() {
		this.image = null;
	}

	public void actualizarAction(String identificacion, String direccion, String nombre, Runnable runnableCerrar) {
		try {
			ModelFactoryController.getInstance().actualizarCliente(identificacion, direccion, nombre, imagePath);
			runnableCerrar.run();
			GestionClientesLogicController.getInstance().regenerarLista();
			new Alert(AlertType.INFORMATION, "El cliente se ha actualizado con éxito").show();
		} catch (Exception e) {
			new Alert(AlertType.WARNING, e.getMessage()).show();
		}
	}
}
