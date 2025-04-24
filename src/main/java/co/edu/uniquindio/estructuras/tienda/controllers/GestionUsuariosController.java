package co.edu.uniquindio.estructuras.tienda.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoDuplicadoException;
import co.edu.uniquindio.estructuras.tienda.exceptions.ElementoNuloException;
import co.edu.uniquindio.estructuras.tienda.logiccontrollers.ModelFactoryController;
import co.edu.uniquindio.estructuras.tienda.model.Rol;
import co.edu.uniquindio.estructuras.tienda.model.Usuario;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

public class GestionUsuariosController implements Initializable {
    @FXML
    private TextField txtNombre, txtCorreo, txtBuscar;
    @FXML
    private ComboBox<Rol> cmbRol;

    @FXML
    private Button btnNuevo, btnGuardar, btnEliminar, btnBuscar;

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, String> colNombre, colCorreo, colRol, colVentas;

    @FXML
    private Pagination pagination;
    @FXML
    private Label lblEstado;

    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private FilteredList<Usuario> listaFiltrada;
    private Usuario usuarioSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar combo box de roles
        cmbRol.setItems(FXCollections.observableArrayList(Rol.values()));
        cmbRol.setConverter(new StringConverter<Rol>() {
            @Override
            public String toString(Rol rol) {
                return rol == null ? "" : rol.getNombre();
            }

            @Override
            public Rol fromString(String string) {
                return string == null ? null : Rol.valueOf(string);
            }
        });

        // Configurar columnas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colVentas.setCellValueFactory(e -> new ReadOnlyStringWrapper(String.valueOf(e.getValue().getVentas().size())));

        // Cargar usuarios desde persistencia
        listaUsuarios.setAll(ModelFactoryController.getInstance().getUsuarios());

        // Configurar lista filtrada
        listaFiltrada = new FilteredList<>(listaUsuarios, p -> true);
        tablaUsuarios.setItems(listaFiltrada);

        // Listener para selección en la tabla
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    usuarioSeleccionado = newSelection;
                    mostrarDetallesUsuario();
                });

        // Inicializar estado
        limpiarFormulario();
    }

    private void mostrarDetallesUsuario() {
        if (usuarioSeleccionado != null) {
            txtNombre.setText(usuarioSeleccionado.getNombre());
            txtCorreo.setText(usuarioSeleccionado.getCorreo());
            cmbRol.setValue(usuarioSeleccionado.getRol());
            lblEstado.setText("Usuario seleccionado: " + usuarioSeleccionado.getNombre());
        }
    }

    @FXML
    private void handleNuevo() {
        limpiarFormulario();
        usuarioSeleccionado = null;
        lblEstado.setText("Nuevo usuario");
    }

    @FXML
    private void handleGuardar() {
        // Selección de imagen debe estar disponible antes de crear usuario

        try {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            Rol rol = cmbRol.getValue();

            if (nombre.isEmpty() || correo.isEmpty() || rol == null) {
                mostrarAlerta("Error", "Todos los campos son obligatorios");
                return;
            }

            if (usuarioSeleccionado == null) {
                // Crear nuevo usuario
                Usuario nuevoUsuario = Usuario.builder()
                        .nombre(nombre)
                        .contrasena("")
                        .rol(rol)
                        .correo(correo)
                        .build();
                ModelFactoryController.getInstance().crearUsuario(nuevoUsuario);
                listaUsuarios.add(nuevoUsuario);
                lblEstado.setText("Usuario creado correctamente");
            } else {
                // Actualizar usuario existente
                usuarioSeleccionado.setNombre(nombre);
                usuarioSeleccionado.setCorreo(correo);
                usuarioSeleccionado.setRol(rol);
                ModelFactoryController.getInstance().actualizarUsuario(usuarioSeleccionado);
                // Refrescar lista desde persistencia
                listaUsuarios.setAll(ModelFactoryController.getInstance().getUsuarios());
                tablaUsuarios.refresh();
                lblEstado.setText("Usuario actualizado correctamente");
            }

            limpiarFormulario();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La cantidad de ventas debe ser un número entero");
        } catch (ElementoDuplicadoException | ElementoNuloException e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        if (usuarioSeleccionado != null) {
            // Eliminar del modelo y persistencia
            ModelFactoryController.getInstance().eliminarUsuario(usuarioSeleccionado.getCorreo());
            listaUsuarios.remove(usuarioSeleccionado);
            limpiarFormulario();
            lblEstado.setText("Usuario eliminado correctamente");
        } else {
            mostrarAlerta("Advertencia", "Debe seleccionar un usuario para eliminar");
        }
    }

    @FXML
    private void handleBuscar() {
        String textoBusqueda = txtBuscar.getText().toLowerCase().trim();

        if (textoBusqueda.isEmpty()) {
            listaFiltrada.setPredicate(p -> true);
        } else {
            listaFiltrada.setPredicate(usuario -> usuario.getNombre().toLowerCase().contains(textoBusqueda) ||
                    usuario.getCorreo().toLowerCase().contains(textoBusqueda) ||
                    usuario.getRol().getNombre().toLowerCase().contains(textoBusqueda));
        }

        lblEstado.setText("Búsqueda completada: " + listaFiltrada.size() + " resultados");
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtCorreo.clear();
        cmbRol.setValue(null);
        usuarioSeleccionado = null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
