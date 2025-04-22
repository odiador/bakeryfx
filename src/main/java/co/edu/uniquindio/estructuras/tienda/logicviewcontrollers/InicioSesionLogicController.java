package co.edu.uniquindio.estructuras.tienda.logicviewcontrollers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoDuplicadoException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNuloException;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.SessionHandler;
import co.edu.uniquindio.estructuras.tienda.model.Rol;
import co.edu.uniquindio.estructuras.tienda.model.Usuario;

public class InicioSesionLogicController {
    private final ModelFactoryController modelFactory = ModelFactoryController.getInstance();

    public void login(String correo, String contrasena, Runnable onSuccess) {
        try {
            Usuario usuario = modelFactory.intentarLoginUsuario(correo, contrasena);
            SessionHandler.getInstance().iniciarSesion(usuario);
            new Alert(AlertType.CONFIRMATION, "Has podido iniciar sesión").show();
            if (onSuccess != null) onSuccess.run();
        } catch (ElementoNoEncontradoException e) {
            new Alert(AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void registrarUsuario(String correo, String nombre, String contrasena, String rol, boolean verificado) {
        try {
            modelFactory.registrarUsuario(correo, nombre, contrasena, Rol.valueOf(rol), verificado);
            new Alert(AlertType.CONFIRMATION, "Has sido registrado con éxito").show();
        } catch (ElementoDuplicadoException | ElementoNuloException e) {
            new Alert(AlertType.ERROR, e.getMessage()).show();
        }
    }
}
