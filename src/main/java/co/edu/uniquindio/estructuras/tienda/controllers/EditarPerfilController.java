package co.edu.uniquindio.estructuras.tienda.controllers;

import co.edu.uniquindio.estructuras.tienda.logiccontrollers.SessionHandler;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.Rol;
import co.edu.uniquindio.estructuras.tienda.model.Usuario;
import co.edu.uniquindio.estructuras.tienda.application.TiendaMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class EditarPerfilController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        usuarioActual = SessionHandler.getInstance().getUsuarioActivo();
        if (usuarioActual != null) {
            nameLabel.setText(usuarioActual.getNombre());
            emailLabel.setText(usuarioActual.getCorreo());
            passwordErrorLabel.setVisible(false);
        }
    }

    @FXML
    void saveButtonAction(ActionEvent event) {
        if (usuarioActual == null || usuarioActual.getRol() != Rol.ADMINISTRADOR) {
            new Alert(AlertType.ERROR, "Solo el administrador puede modificar la contraseña.").show();
            return;
        }
        String nueva = newPasswordField.getText();
        String confirmar = confirmPasswordField.getText();
        if (nueva == null || nueva.isEmpty() || confirmar == null || confirmar.isEmpty()) {
            passwordErrorLabel.setText("La nueva contraseña y la confirmación no pueden estar vacías.");
            passwordErrorLabel.setVisible(true);
            return;
        }
        if (!nueva.equals(confirmar)) {
            passwordErrorLabel.setText("Las contraseñas no coinciden");
            passwordErrorLabel.setVisible(true);
            return;
        }
        usuarioActual.setContrasena(nueva);
        try {
            // Persistir el cambio en la base de datos o almacenamiento
            ModelFactoryController.getInstance().actualizarUsuario(usuarioActual);
            passwordErrorLabel.setVisible(false);
            new Alert(AlertType.INFORMATION, "Contraseña actualizada correctamente.").show();
            newPasswordField.clear();
            confirmPasswordField.clear();
            // Opcional: volver al dashboard
            // co.edu.uniquindio.estructuras.tienda.logicviewcontrollers.MenuPrincipalLogicController.getInstance().irADashboard();
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Error al guardar los cambios: " + e.getMessage()).show();
        }
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        TiendaMain.cambiarAVistaMenuPrincipal();
    }
}
