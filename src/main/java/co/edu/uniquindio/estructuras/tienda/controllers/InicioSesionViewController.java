package co.edu.uniquindio.estructuras.tienda.controllers;

import co.edu.uniquindio.estructuras.tienda.logicviewcontrollers.InicioSesionLogicController;
import co.edu.uniquindio.estructuras.tienda.application.TiendaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InicioSesionViewController {
    private InicioSesionLogicController controller = new InicioSesionLogicController();
    @FXML
    private Button btnSignIn;

    @FXML
    private TextField tfMail;

    @FXML
    private PasswordField tfPassword;

    @FXML
    void onForgotPasswordPressed(ActionEvent event) {

    }

    @FXML
    void onRegisterPressed(ActionEvent event) {

    }

    @FXML
    void onSignInPressed(ActionEvent event) {
        controller.login(tfMail.getText(), tfPassword.getText(), () -> TiendaMain.cambiarAVistaMenuPrincipal());
    }

}
