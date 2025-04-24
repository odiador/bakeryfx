package co.edu.uniquindio.estructuras.tienda.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.estructuras.tienda.application.TiendaMain;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.SessionHandler;
import co.edu.uniquindio.estructuras.tienda.logicviewcontrollers.MenuPrincipalLogicController;
import co.edu.uniquindio.estructuras.tienda.model.Rol;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;

public class MenuPrincipalController implements Initializable {

	@FXML
	private Button btnLogout;
	@FXML
	private Button btnGestionUsuarios;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MenuPrincipalLogicController.getInstance().cargarTransicionCargando(svg1, svg2);
		MenuPrincipalLogicController.getInstance().cargarAnimacionCargando(scrollLoading);
		MenuPrincipalLogicController.getInstance().cargarMenuCentral(mainLayer);
		MenuPrincipalLogicController.getInstance().inicializarPerspectivas();
		MenuPrincipalLogicController.getInstance().inicializarListeners(svgShoppingCard);
		ModelFactoryController.getInstance().cargarCarrito();
		// Mostrar botón de gestión solo si el usuario es admin
		if (btnGestionUsuarios != null) {
			var usuario = SessionHandler.getInstance().getUsuarioActivo();
			btnGestionUsuarios.setVisible(usuario != null && usuario.getRol() == Rol.ADMINISTRADOR);
		}
		// Mostrar dashboard al iniciar
		MenuPrincipalLogicController.getInstance().mostrarDashboardInicial();
	}

	@FXML
	private void logoutEvent(ActionEvent event) {
		SessionHandler.getInstance().cerrarSesion();
		TiendaMain.cambiarAVistaLogin();
	}

	@FXML
	private void gestionUsuariosEvent(ActionEvent event) {
		// Aquí puedes abrir la ventana de gestión de usuarios
		// Por ejemplo: TiendaMain.cambiarAVistaGestionUsuarios();
		System.out.println("gestionUsuariosEvent ejecutado");
	}

	@FXML
	private SVGPath svgShoppingCard;

	@FXML
	private SVGPath svg1, svg2;

	@FXML
	private BorderPane loadingLayer, mainLayer, searchLayer, menuLayer;

	@FXML
	private TextField tfBusqueda;

	@FXML
	private ScrollPane scrollLoading;

	@FXML
	void buscarEvent(ActionEvent event) {
		buscarAction();
	}

	@FXML
	void clientesEvent(ActionEvent event) {
		clientesAction();
	}

	@FXML
	void helpEvent(ActionEvent event) {
		helpAction();
	}

	@FXML
	void productsEvent(ActionEvent event) {
		productsAction();
	}

	@FXML
	void profileEvent(ActionEvent event) {
		profileAction();
	}

	@FXML
	void shoppingCardEvent(ActionEvent event) {
		shoppingCardAction();
	}

	@FXML
	void ventasEvent(ActionEvent event) {
		ventasAction();
	}

	@FXML
	void pastelesEvent(ActionEvent event) {
		MenuPrincipalLogicController.getInstance().irAPasteles();
	}

	@FXML
	void menuEvent(ActionEvent event) {
		menuAction();
	}

	@FXML
	void cerrarMenuIzqEvent(ActionEvent event) {
		MenuPrincipalLogicController.getInstance().ocultarMenuIzq(menuLayer);
	}

	@FXML
	void editarPerfilEvent(ActionEvent event) {
		System.out.println("editarPerfilEvent ejecutado");
	}

	@FXML
	void cancelarEvent(ActionEvent event) {
		MenuPrincipalLogicController.getInstance().cancelarAction(searchLayer, tfBusqueda);
	}

	@FXML
	void buscarProcuctoEvent(ActionEvent event) {
		MenuPrincipalLogicController.getInstance().buscarProductoAction(searchLayer, tfBusqueda);
	}

	@FXML
	void buscarClienteEvent(ActionEvent event) {
		MenuPrincipalLogicController.getInstance().buscarClienteAction(searchLayer, tfBusqueda);
	}

	@FXML
	void dashboardActionEvent(ActionEvent event) {
		MenuPrincipalLogicController.getInstance().irADashboard();
	}

	private void clientesAction() {
		MenuPrincipalLogicController.getInstance().irAClientes();
	}

	private void buscarAction() {
		MenuPrincipalLogicController.getInstance().mostrarCapaBuscar(searchLayer, tfBusqueda);
	}

	private void helpAction() {
		MenuPrincipalLogicController.getInstance().mostrarInfo();
	}

	private void productsAction() {
		MenuPrincipalLogicController.getInstance().irAProductos();
	}

	private void profileAction() {
		System.out.println("profileAction ejecutado");
	}

	private void menuAction() {
		MenuPrincipalLogicController.getInstance().mostrarMenuIzq(menuLayer);
	}

	private void shoppingCardAction() {
		MenuPrincipalLogicController.getInstance().mostrarOcultarCarrito();
	}

	private void ventasAction() {
		MenuPrincipalLogicController.getInstance().irReportes();
	}
}
