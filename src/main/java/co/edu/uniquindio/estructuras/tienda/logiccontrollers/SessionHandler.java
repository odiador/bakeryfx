package co.edu.uniquindio.estructuras.tienda.logiccontrollers;

import co.edu.uniquindio.estructuras.tienda.model.Usuario;

public class SessionHandler {
    private static SessionHandler instance;
    private Usuario usuarioActivo;

    private SessionHandler() {}

    public static SessionHandler getInstance() {
        if (instance == null) {
            instance = new SessionHandler();
        }
        return instance;
    }

    public void iniciarSesion(Usuario usuario) {
        this.usuarioActivo = usuario;
    }

    public void cerrarSesion() {
        this.usuarioActivo = null;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public boolean haySesionActiva() {
        return usuarioActivo != null;
    }
}
