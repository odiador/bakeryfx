package co.edu.uniquindio.estructuras.tienda.tests;

import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.Rol;
import co.edu.uniquindio.estructuras.tienda.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdminCreationTest {

    //@Test
    public void crearAdministradorTest() throws Exception {
        ModelFactoryController model = ModelFactoryController.getInstance();
        String correo = "arroa03@gmail.com";
        String nombre = "Juan Manuel Amador Roa";
        String contrasena = "admin123";
        Rol rol = Rol.ADMINISTRADOR;
        boolean verificado = true;

        Usuario admin = model.registrarUsuario(correo, nombre, contrasena, rol, verificado);

        Assertions.assertNotNull(admin);
        Assertions.assertEquals(correo, admin.getCorreo());
        Assertions.assertEquals(nombre, admin.getNombre());
        Assertions.assertEquals(contrasena, admin.getContrasena());
        Assertions.assertEquals(rol, admin.getRol());
        Assertions.assertTrue(admin.isVerificado());
    }
}
