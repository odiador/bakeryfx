package co.edu.uniquindio.estructuras.tienda.application;

import java.io.IOException;

import co.edu.uniquindio.estructuras.tienda.utils.FxmlPerspective;
import co.edu.uniquindio.estructuras.tienda.application.TiendaMain;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * JavaFX App
 */
public class TiendaMain extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) throws IOException {
		TiendaMain.stage = stage;
		FxmlPerspective perspective = FxmlPerspective.loadPerspective("signin");
		Scene scene = new Scene(perspective.getPerspective());
		stage.setMinWidth(720);
		stage.setMinHeight(405);
		stage.setScene(scene);
		stage.show();
		try {
			new ProductoPanaderiaTest().agregarProductosPanaderiaConModelFactory();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void cambiarAVistaMenuPrincipal() {
		try {
			FxmlPerspective perspective = FxmlPerspective.loadPerspective("menuprincipal");
			Scene scene = new Scene(perspective.getPerspective());
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cambiarAVistaLogin() {
		try {
			FxmlPerspective perspective = FxmlPerspective.loadPerspective("signin");
			Scene scene = new Scene(perspective.getPerspective());
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Para el futuro
	public static void cambiarAVistaGestionUsuarios() {
		// Implementar cuando exista la vista
	}

	public static void main(String[] args) {
		launch();
	}

	public static Window getCurrentStage() {
		return stage;
	}

}